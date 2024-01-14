package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.interfaces.IBOCtxSettings;

public interface ITechCtxSettingsLoc extends IBOCtxSettings {

   public static final int CTX_LOC_BASIC_SIZE            = CTX_BASIC_SIZE + 5;

   public static final int CTX_LOC_OFFSET_01_FLAG1       = CTX_BASIC_SIZE;

   /**
    * ID in the local array of the suffix.. language name is next index
    */
   public static final int CTX_LOC_OFFSET_02_LOC_ID2     = CTX_BASIC_SIZE + 1;


}
