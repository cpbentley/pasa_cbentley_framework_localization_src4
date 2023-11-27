package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.tech.ITechCtxSettings;

public interface ITechCtxSettingsLocalization extends ITechCtxSettings {

   public static final int MODSET_LOC_BASIC_SIZE              = CTX_BASIC_SIZE + 5;

   /**
    * 
    */
   public static final int MODSET_LOC_FLAG_1_USER_IMAGE_CACHE = 1 << 0;

   public static final int MODSET_LOC_OFFSET_01_FLAG1         = CTX_BASIC_SIZE;

   /**
    * 
    */
   public static final int MODSET_LOC_OFFSET_02_              = CTX_BASIC_SIZE + 1;

}
