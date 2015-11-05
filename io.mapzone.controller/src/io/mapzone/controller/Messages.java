package io.mapzone.controller;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.runtime.i18n.MessagesImpl;

/**
 * The messages of the <code>io.mapzone.controller</code> plugin.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a> 
 */
public class Messages {

    private static final String BUNDLE_NAME = ControllerPlugin.ID + ".messages"; //$NON-NLS-1$

    private static final MessagesImpl instance = new MessagesImpl( BUNDLE_NAME, Messages.class.getClassLoader() );

    public static IMessages forPrefix( String prefix ) {
        return instance.forPrefix( prefix );
    }

    // instance *******************************************
    
    private Messages() {
        // prevent instantiation
    }

//    public static String get( String key, Object... args ) {
//        return instance.get( key, args );
//    }
//
//    public static String get2( Object caller, String key, Object... args ) {
//        return instance.get( caller, key, args );
//    }
//    
//    public static Messages get() {
//        Class clazz = Messages.class;
//        return (Messages)RWT.NLS.getISO8859_1Encoded( BUNDLE_NAME, clazz );
//    }

}
