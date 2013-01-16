import java.util.regex.Matcher
import java.util.regex.Pattern



def data = loadRawData("MestaCut.log");

// Write the transformed structure to a file.
def fosTrans = new FileOutputStream('TransformedSave.txt');
data.eachWithIndex { it, i ->
  fosTrans.write( ("$i: "+it.toString()+"\n").getBytes() );
}
fosTrans.close();


// Create the cells.
def fos = new FileOutputStream('debug.txt');
//(65..128).each { num ->
//  def it = data[num];
data.each {
  Cell cell = Cell.create( it[0], it[1], it[2], it[3], it[4], it[5], it[6], it[7] );
  if( cell != null ){
      //println cell;
      fos.println( cell );
  }
}
fos.close();



return;





int convertPartIndexIndex( Object chr ){
  //int i = (chr as char) as int;
  int i = chr;
  i -= ('0' as char);
  if( i > 0  &&  i < 8 ) return i;
  if( i == 36 ) return 0; // T
  if( i == 35 ) return 8  // S
  throw new Exception('Unknown part index.');
}



List loadRawData( String filePath ){
    def data = new ArrayList(1024);
    Matcher matcher = "" =~ /Load_MAP([0-9ST])\(\): ([0-9]+) ([0-9]+).*/ ;
    
    
    final PROCESS_LINES = 30000;
    def i = 1;
    new File( filePath ).eachLine {  ln -> 
      ln = ln.trim();
      while( true ) {
          if( ln.size() == 0 ) break;
          if( i++ > PROCESS_LINES ) return;
          
          matcher.reset( ln );
          if( ! matcher.matches() ) break;
          
          int index = matcher[0][2].toInteger();
          int value = matcher[0][3].toInteger();
          //int part  = matcher[0][1]; part -= ('0' as char);
          int part = convertPartIndexIndex( matcher[0][1] );
          //println "L: $part Index: $index Value: $value";
    
          if( null == data[index] )
              data[index] = new ArrayList(8);
          data[index][part] = value;
          break;
      } // while( false );
    }
    
    println data.size;
    println data[0].size;
    
    return data;
}




/*
 * Helpers
 */
boolean isBitSet( int value, int index ){
    return ((value >> index) & 1) != 0;
}



public class Cell {

    enum CellClass { GROUND, RAIL, ROAD, BUILDING, TREES, STATION, WATER, VOID, INDUSTRY, TUNNEL, VARIOUS }
    enum OwnerType { PLAYER, TOWN, NOBODY }
    
    CellClass cls;
    byte height;
    OwnerType ownerType;
    int owner;
    
    static Cell create( int th, m1, m2, m3, m4, m5, m6, m7 ){
        CellClass cls;
        Cell cell;
        // Tile class.
        byte typeNum = th >> 4;
        //println "Cls: $typeNum"
        switch( typeNum ){
            case 0: cls = CellClass.GROUND;   cell = new GroundCell(); break;
            case 1: cls = CellClass.RAIL;     break;
            case 2: cls = CellClass.ROAD;     cell = createRoadCell    ( m1, m2, m3, m4, m5, m6, m7 ); break;
            case 3: cls = CellClass.BUILDING; cell = createBuildingCell( m1, m2, m3, m4, m5, m6, m7 ); break;
            case 4: cls = CellClass.TREES;    break;
            case 5: cls = CellClass.STATION;  break;
            case 6: cls = CellClass.WATER;    break;
            // Tiles of this class form an invisible, one tile wide border at the south (bottom) edges of the map,
            // so as to protect several algorithms from the consequences of a wraparound at the edges. 
            case 7: cls = CellClass.VOID;    break;
            case 8: cls = CellClass.INDUSTRY; break;
            case 9: cls = CellClass.TUNNEL;   break;
            case 10: cls = CellClass.VARIOUS; break;
        }
        if( null == cell )
            return null;
            
        // North corner height.
        cell.height = th & 0xF;
        cell.owner = m1; // TODO
        
        return cell;
    }
    
    
    /**
     *  Road
     */
    static RoadCell createRoadCell( int m1, m2, m3, m4, m5, m6, m7 ){
        RoadCell cell = new RoadCell();
        // m5 bit 7 clear: road or level-crossing 
        if( ! isBitSet(m5, 7) ){
            // m5 bit 6 clear: road 
            if( ! isBitSet(m5, 6) ){
                // m5 bits 3..0: road layout road type 0 (normal road): bit set = road piece present:
                //bit 0-3: NW, SW, SE NE
                cell.dirs = m5 & 0xFF;
            }
        }
        // m5 bit 7 set, bit 6 clear: road depot
        else {
            if( ! isBitSet(m5, 6) ){ /* Road depot */ }
        }
        return cell;
    }
    
    /**
     *  Building
     */
    static BuildingCell createBuildingCell( int m1, m2, m3, m4, m5, m6, m7 ){
        BuildingCell cell = new BuildingCell();
        cell.owner = m2;
        //m4 : town building type (with m3[6] bit)
        cell.buildingType = m4 | (m3 >> 5 & 0x1);
        return cell;
    }

}

public class GroundCell extends Cell {}

public class RoadCell extends Cell {
    byte dirs;
    
    String toString(){
        return "RoadCell{ dirs: $dirs }"
    }
}

public class BuildingCell extends Cell {
    byte buildingType;
    
    String toString(){
        return "BuildingCell{ buildingType: "+this.buildingType+" }";
    }
}