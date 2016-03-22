/* 
 * mapzone.io
 * Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.vm.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultBoolean;
import org.polymap.core.runtime.config.Mandatory;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class Script
        extends ScriptElement {

    private static Log log = LogFactory.getLog( Script.class );
    
    /**
     * False means kick off the script in the background, but don't wait for it to
     * finish. (as of version 1.1.0, implemented as nohup)
     */
    @Mandatory
    @DefaultBoolean( true )
    public Config2<Script,Boolean>  blockOnComplete;
    
    @Mandatory
    @DefaultBoolean( false )
    public Config2<Script,Boolean>  exceptionOnFail;
    
//    @Mandatory
//    @DefaultBoolean( false )
//    public Config2<Script,Boolean>  nohup;
    
    private List<Statement>         statements = new ArrayList();
    
    
    public Script add( String command ) {
        statements.add( new SimpleStatement( command ) );
        return this;
    }
    
    
    @Override
    public String render() {
        return Joiner.on( "\n" ).join( statements );
    }


    /**
     * 
     */
    public abstract class Statement
            extends ScriptElement {
        
    }

    
    /**
     * 
     */
    public class SimpleStatement
            extends Statement {
    
        private String      command;

        public SimpleStatement( String command ) {
            this.command = command;
        }

        @Override
        public String render() {
            return command;
        }
    }
    
}
