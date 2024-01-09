package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;

public interface IConfigLoc extends IConfigBO {

   /**
    * Starting local suffix. It must be known in the hardcoded list of {@link LocalizationCtx}.
    * When null, use {@link LocalizationCtx#SUFFIX_01_EN}
    * 
    * Suffix must exist in supported locales
    * @return
    */
   public String getLocalSuffix();
   
   /**
    * For speed or testing purposes, remove multi local support and force every module to use 
    * this language.
    * 
    * When settings are not null, root file paths are not read, the language saved in settings is used
    * 
    * if suffix file is not found, reverts back to en.
    * @return
    */
   public String getSingleLocaleSuffix();

   /**
    * Array containing suffix/language name pairs.
    * 
    * even size
    * @return
    */
   public String[] getSupportedLocales();
   
   
}
