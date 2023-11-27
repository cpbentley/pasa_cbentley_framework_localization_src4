package pasa.cbentley.framework.localization.src4.engine;

public class LocUtils {

   /**
    * TODO remove this. no need anymore with unique static constants
    * Returns a unique String identifier. The module is stored in the high bits.
    * <br>
    * <br>
    * Line number is actually id + 2. +1 cuz of first line comment, +1 because lines are 1 index based instead of 0-index based
    * <br>
    * @param ctxID  the id of the charcollector
    * @param id the id of the string. i.e the line number on which it is located
    * @return
    */
   public static int getID(int ctxID, int id) {
      return ((ctxID & 0xFFFF) << 16) + (id & 0x0000FFFF);
   }

   public static int getCtxIDFromLocID(int locID) {
      return (locID >> 16) & 0xFFFF;
   }
   
   public static int getStrIDFromLocID(int locID) {
      return locID & 0x0000FFFF;
   }
}
