package io.mapzone.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import io.mapzone.services.GeoJSONGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalGeoJSONParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_STRING", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'\"Point\"'", "'\"MultiPoint\"'", "'\"LineString\"'", "'\"MultiLineString\"'", "'\"Polygon\"'", "'\"MultiPolygon\"'", "'\"GeometryCollection\"'", "'\"Feature\"'", "'\"FeatureCollection\"'", "'\"name\"'", "'\"link\"'", "'\"proj4\"'", "'\"ogcwkt\"'", "'\"esriwkt\"'", "'{'", "'}'", "'\"type\"'", "':'", "','", "'\"crs\"'", "'\"bbox\"'", "'\"geometry\"'", "'\"geometries\"'", "'\"properties\"'", "'\"style\"'", "'\"features\"'", "'['", "']'", "'\"href\"'", "'\"coordinates\"'", "'-'", "'.'"
    };
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__37=37;
    public static final int T__16=16;
    public static final int T__38=38;
    public static final int T__17=17;
    public static final int T__39=39;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__33=33;
    public static final int T__12=12;
    public static final int T__34=34;
    public static final int T__13=13;
    public static final int T__35=35;
    public static final int T__14=14;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=6;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=4;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__20=20;
    public static final int T__42=42;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalGeoJSONParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGeoJSONParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalGeoJSONParser.tokenNames; }
    public String getGrammarFileName() { return "../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g"; }


     
     	private GeoJSONGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(GeoJSONGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleGeoJSON"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:60:1: entryRuleGeoJSON : ruleGeoJSON EOF ;
    public final void entryRuleGeoJSON() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:61:1: ( ruleGeoJSON EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:62:1: ruleGeoJSON EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONRule()); 
            }
            pushFollow(FOLLOW_ruleGeoJSON_in_entryRuleGeoJSON61);
            ruleGeoJSON();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleGeoJSON68); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGeoJSON"


    // $ANTLR start "ruleGeoJSON"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:69:1: ruleGeoJSON : ( ( rule__GeoJSON__Group__0 ) ) ;
    public final void ruleGeoJSON() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:73:2: ( ( ( rule__GeoJSON__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:74:1: ( ( rule__GeoJSON__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:74:1: ( ( rule__GeoJSON__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:75:1: ( rule__GeoJSON__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:76:1: ( rule__GeoJSON__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:76:2: rule__GeoJSON__Group__0
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group__0_in_ruleGeoJSON94);
            rule__GeoJSON__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGeoJSON"


    // $ANTLR start "entryRuleCRS"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:88:1: entryRuleCRS : ruleCRS EOF ;
    public final void entryRuleCRS() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:89:1: ( ruleCRS EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:90:1: ruleCRS EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSRule()); 
            }
            pushFollow(FOLLOW_ruleCRS_in_entryRuleCRS121);
            ruleCRS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleCRS128); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCRS"


    // $ANTLR start "ruleCRS"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:97:1: ruleCRS : ( ( rule__CRS__Group__0 ) ) ;
    public final void ruleCRS() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:101:2: ( ( ( rule__CRS__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:102:1: ( ( rule__CRS__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:102:1: ( ( rule__CRS__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:103:1: ( rule__CRS__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:104:1: ( rule__CRS__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:104:2: rule__CRS__Group__0
            {
            pushFollow(FOLLOW_rule__CRS__Group__0_in_ruleCRS154);
            rule__CRS__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCRS"


    // $ANTLR start "entryRuleCoordList"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:116:1: entryRuleCoordList : ruleCoordList EOF ;
    public final void entryRuleCoordList() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:117:1: ( ruleCoordList EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:118:1: ruleCoordList EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListRule()); 
            }
            pushFollow(FOLLOW_ruleCoordList_in_entryRuleCoordList181);
            ruleCoordList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleCoordList188); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCoordList"


    // $ANTLR start "ruleCoordList"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:125:1: ruleCoordList : ( ( rule__CoordList__Group__0 ) ) ;
    public final void ruleCoordList() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:129:2: ( ( ( rule__CoordList__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:130:1: ( ( rule__CoordList__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:130:1: ( ( rule__CoordList__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:131:1: ( rule__CoordList__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:132:1: ( rule__CoordList__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:132:2: rule__CoordList__Group__0
            {
            pushFollow(FOLLOW_rule__CoordList__Group__0_in_ruleCoordList214);
            rule__CoordList__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCoordList"


    // $ANTLR start "entryRuleGeometry"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:144:1: entryRuleGeometry : ruleGeometry EOF ;
    public final void entryRuleGeometry() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:145:1: ( ruleGeometry EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:146:1: ruleGeometry EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryRule()); 
            }
            pushFollow(FOLLOW_ruleGeometry_in_entryRuleGeometry241);
            ruleGeometry();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleGeometry248); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGeometry"


    // $ANTLR start "ruleGeometry"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:153:1: ruleGeometry : ( ( rule__Geometry__Group__0 ) ) ;
    public final void ruleGeometry() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:157:2: ( ( ( rule__Geometry__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:158:1: ( ( rule__Geometry__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:158:1: ( ( rule__Geometry__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:159:1: ( rule__Geometry__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:160:1: ( rule__Geometry__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:160:2: rule__Geometry__Group__0
            {
            pushFollow(FOLLOW_rule__Geometry__Group__0_in_ruleGeometry274);
            rule__Geometry__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGeometry"


    // $ANTLR start "entryRuleCoord"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:172:1: entryRuleCoord : ruleCoord EOF ;
    public final void entryRuleCoord() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:173:1: ( ruleCoord EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:174:1: ruleCoord EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordRule()); 
            }
            pushFollow(FOLLOW_ruleCoord_in_entryRuleCoord301);
            ruleCoord();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleCoord308); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCoord"


    // $ANTLR start "ruleCoord"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:181:1: ruleCoord : ( ( rule__Coord__Alternatives ) ) ;
    public final void ruleCoord() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:185:2: ( ( ( rule__Coord__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:186:1: ( ( rule__Coord__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:186:1: ( ( rule__Coord__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:187:1: ( rule__Coord__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:188:1: ( rule__Coord__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:188:2: rule__Coord__Alternatives
            {
            pushFollow(FOLLOW_rule__Coord__Alternatives_in_ruleCoord334);
            rule__Coord__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCoord"


    // $ANTLR start "entryRuleCoordArray"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:200:1: entryRuleCoordArray : ruleCoordArray EOF ;
    public final void entryRuleCoordArray() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:201:1: ( ruleCoordArray EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:202:1: ruleCoordArray EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayRule()); 
            }
            pushFollow(FOLLOW_ruleCoordArray_in_entryRuleCoordArray361);
            ruleCoordArray();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleCoordArray368); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCoordArray"


    // $ANTLR start "ruleCoordArray"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:209:1: ruleCoordArray : ( ( rule__CoordArray__Group__0 ) ) ;
    public final void ruleCoordArray() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:213:2: ( ( ( rule__CoordArray__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:214:1: ( ( rule__CoordArray__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:214:1: ( ( rule__CoordArray__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:215:1: ( rule__CoordArray__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:216:1: ( rule__CoordArray__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:216:2: rule__CoordArray__Group__0
            {
            pushFollow(FOLLOW_rule__CoordArray__Group__0_in_ruleCoordArray394);
            rule__CoordArray__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCoordArray"


    // $ANTLR start "entryRuleStyle"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:228:1: entryRuleStyle : ruleStyle EOF ;
    public final void entryRuleStyle() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:229:1: ( ruleStyle EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:230:1: ruleStyle EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleRule()); 
            }
            pushFollow(FOLLOW_ruleStyle_in_entryRuleStyle421);
            ruleStyle();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleStyle428); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStyle"


    // $ANTLR start "ruleStyle"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:237:1: ruleStyle : ( ( rule__Style__Group__0 ) ) ;
    public final void ruleStyle() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:241:2: ( ( ( rule__Style__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:242:1: ( ( rule__Style__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:242:1: ( ( rule__Style__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:243:1: ( rule__Style__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:244:1: ( rule__Style__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:244:2: rule__Style__Group__0
            {
            pushFollow(FOLLOW_rule__Style__Group__0_in_ruleStyle454);
            rule__Style__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStyle"


    // $ANTLR start "entryRuleFeature"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:256:1: entryRuleFeature : ruleFeature EOF ;
    public final void entryRuleFeature() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:257:1: ( ruleFeature EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:258:1: ruleFeature EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureRule()); 
            }
            pushFollow(FOLLOW_ruleFeature_in_entryRuleFeature481);
            ruleFeature();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeature488); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFeature"


    // $ANTLR start "ruleFeature"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:265:1: ruleFeature : ( ( rule__Feature__Group__0 ) ) ;
    public final void ruleFeature() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:269:2: ( ( ( rule__Feature__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:270:1: ( ( rule__Feature__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:270:1: ( ( rule__Feature__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:271:1: ( rule__Feature__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:272:1: ( rule__Feature__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:272:2: rule__Feature__Group__0
            {
            pushFollow(FOLLOW_rule__Feature__Group__0_in_ruleFeature514);
            rule__Feature__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFeature"


    // $ANTLR start "entryRuleStruct"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:284:1: entryRuleStruct : ruleStruct EOF ;
    public final void entryRuleStruct() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:285:1: ( ruleStruct EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:286:1: ruleStruct EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructRule()); 
            }
            pushFollow(FOLLOW_ruleStruct_in_entryRuleStruct541);
            ruleStruct();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleStruct548); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStruct"


    // $ANTLR start "ruleStruct"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:293:1: ruleStruct : ( ( rule__Struct__Group__0 ) ) ;
    public final void ruleStruct() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:297:2: ( ( ( rule__Struct__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:298:1: ( ( rule__Struct__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:298:1: ( ( rule__Struct__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:299:1: ( rule__Struct__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:300:1: ( rule__Struct__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:300:2: rule__Struct__Group__0
            {
            pushFollow(FOLLOW_rule__Struct__Group__0_in_ruleStruct574);
            rule__Struct__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStruct"


    // $ANTLR start "entryRuleProperty"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:312:1: entryRuleProperty : ruleProperty EOF ;
    public final void entryRuleProperty() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:313:1: ( ruleProperty EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:314:1: ruleProperty EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyRule()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty601);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty608); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:321:1: ruleProperty : ( ( rule__Property__Group__0 ) ) ;
    public final void ruleProperty() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:325:2: ( ( ( rule__Property__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:326:1: ( ( rule__Property__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:326:1: ( ( rule__Property__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:327:1: ( rule__Property__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:328:1: ( rule__Property__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:328:2: rule__Property__Group__0
            {
            pushFollow(FOLLOW_rule__Property__Group__0_in_ruleProperty634);
            rule__Property__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleValue"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:340:1: entryRuleValue : ruleValue EOF ;
    public final void entryRuleValue() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:341:1: ( ruleValue EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:342:1: ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_ruleValue_in_entryRuleValue661);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleValue668); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:349:1: ruleValue : ( ( rule__Value__Alternatives ) ) ;
    public final void ruleValue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:353:2: ( ( ( rule__Value__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:354:1: ( ( rule__Value__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:354:1: ( ( rule__Value__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:355:1: ( rule__Value__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:356:1: ( rule__Value__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:356:2: rule__Value__Alternatives
            {
            pushFollow(FOLLOW_rule__Value__Alternatives_in_ruleValue694);
            rule__Value__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleArray"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:368:1: entryRuleArray : ruleArray EOF ;
    public final void entryRuleArray() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:369:1: ( ruleArray EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:370:1: ruleArray EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayRule()); 
            }
            pushFollow(FOLLOW_ruleArray_in_entryRuleArray721);
            ruleArray();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArray728); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleArray"


    // $ANTLR start "ruleArray"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:377:1: ruleArray : ( ( rule__Array__Group__0 ) ) ;
    public final void ruleArray() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:381:2: ( ( ( rule__Array__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:382:1: ( ( rule__Array__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:382:1: ( ( rule__Array__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:383:1: ( rule__Array__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:384:1: ( rule__Array__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:384:2: rule__Array__Group__0
            {
            pushFollow(FOLLOW_rule__Array__Group__0_in_ruleArray754);
            rule__Array__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleArray"


    // $ANTLR start "entryRuleNINT"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:396:1: entryRuleNINT : ruleNINT EOF ;
    public final void entryRuleNINT() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:397:1: ( ruleNINT EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:398:1: ruleNINT EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNINTRule()); 
            }
            pushFollow(FOLLOW_ruleNINT_in_entryRuleNINT781);
            ruleNINT();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNINTRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleNINT788); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNINT"


    // $ANTLR start "ruleNINT"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:405:1: ruleNINT : ( ( rule__NINT__Group__0 ) ) ;
    public final void ruleNINT() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:409:2: ( ( ( rule__NINT__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:410:1: ( ( rule__NINT__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:410:1: ( ( rule__NINT__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:411:1: ( rule__NINT__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNINTAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:412:1: ( rule__NINT__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:412:2: rule__NINT__Group__0
            {
            pushFollow(FOLLOW_rule__NINT__Group__0_in_ruleNINT814);
            rule__NINT__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNINTAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNINT"


    // $ANTLR start "entryRuleDOUBLE"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:424:1: entryRuleDOUBLE : ruleDOUBLE EOF ;
    public final void entryRuleDOUBLE() throws RecognitionException {
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:425:1: ( ruleDOUBLE EOF )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:426:1: ruleDOUBLE EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLERule()); 
            }
            pushFollow(FOLLOW_ruleDOUBLE_in_entryRuleDOUBLE841);
            ruleDOUBLE();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLERule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDOUBLE848); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDOUBLE"


    // $ANTLR start "ruleDOUBLE"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:433:1: ruleDOUBLE : ( ( rule__DOUBLE__Group__0 ) ) ;
    public final void ruleDOUBLE() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:437:2: ( ( ( rule__DOUBLE__Group__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:438:1: ( ( rule__DOUBLE__Group__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:438:1: ( ( rule__DOUBLE__Group__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:439:1: ( rule__DOUBLE__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLEAccess().getGroup()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:440:1: ( rule__DOUBLE__Group__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:440:2: rule__DOUBLE__Group__0
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group__0_in_ruleDOUBLE874);
            rule__DOUBLE__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLEAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDOUBLE"


    // $ANTLR start "ruleType"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:453:1: ruleType : ( ( rule__Type__Alternatives ) ) ;
    public final void ruleType() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:457:1: ( ( ( rule__Type__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:458:1: ( ( rule__Type__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:458:1: ( ( rule__Type__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:459:1: ( rule__Type__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:460:1: ( rule__Type__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:460:2: rule__Type__Alternatives
            {
            pushFollow(FOLLOW_rule__Type__Alternatives_in_ruleType911);
            rule__Type__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleType"


    // $ANTLR start "ruleCRSType"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:472:1: ruleCRSType : ( ( rule__CRSType__Alternatives ) ) ;
    public final void ruleCRSType() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:476:1: ( ( ( rule__CRSType__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:477:1: ( ( rule__CRSType__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:477:1: ( ( rule__CRSType__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:478:1: ( rule__CRSType__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSTypeAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:479:1: ( rule__CRSType__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:479:2: rule__CRSType__Alternatives
            {
            pushFollow(FOLLOW_rule__CRSType__Alternatives_in_ruleCRSType947);
            rule__CRSType__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSTypeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCRSType"


    // $ANTLR start "ruleLinkCRSType"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:491:1: ruleLinkCRSType : ( ( rule__LinkCRSType__Alternatives ) ) ;
    public final void ruleLinkCRSType() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:495:1: ( ( ( rule__LinkCRSType__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:496:1: ( ( rule__LinkCRSType__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:496:1: ( ( rule__LinkCRSType__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:497:1: ( rule__LinkCRSType__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLinkCRSTypeAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:498:1: ( rule__LinkCRSType__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:498:2: rule__LinkCRSType__Alternatives
            {
            pushFollow(FOLLOW_rule__LinkCRSType__Alternatives_in_ruleLinkCRSType983);
            rule__LinkCRSType__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLinkCRSTypeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLinkCRSType"


    // $ANTLR start "ruleGeometryType"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:510:1: ruleGeometryType : ( ( rule__GeometryType__Alternatives ) ) ;
    public final void ruleGeometryType() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:514:1: ( ( ( rule__GeometryType__Alternatives ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:515:1: ( ( rule__GeometryType__Alternatives ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:515:1: ( ( rule__GeometryType__Alternatives ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:516:1: ( rule__GeometryType__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryTypeAccess().getAlternatives()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:517:1: ( rule__GeometryType__Alternatives )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:517:2: rule__GeometryType__Alternatives
            {
            pushFollow(FOLLOW_rule__GeometryType__Alternatives_in_ruleGeometryType1019);
            rule__GeometryType__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryTypeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGeometryType"


    // $ANTLR start "rule__GeoJSON__Alternatives_1_3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:528:1: rule__GeoJSON__Alternatives_1_3 : ( ( ( rule__GeoJSON__Group_1_3_0__0 ) ) | ( ( rule__GeoJSON__Group_1_3_1__0 ) ) );
    public final void rule__GeoJSON__Alternatives_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:532:1: ( ( ( rule__GeoJSON__Group_1_3_0__0 ) ) | ( ( rule__GeoJSON__Group_1_3_1__0 ) ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==32) ) {
                alt1=1;
            }
            else if ( (LA1_0==33) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:533:1: ( ( rule__GeoJSON__Group_1_3_0__0 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:533:1: ( ( rule__GeoJSON__Group_1_3_0__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:534:1: ( rule__GeoJSON__Group_1_3_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_3_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:535:1: ( rule__GeoJSON__Group_1_3_0__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:535:2: rule__GeoJSON__Group_1_3_0__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__0_in_rule__GeoJSON__Alternatives_1_31054);
                    rule__GeoJSON__Group_1_3_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:539:6: ( ( rule__GeoJSON__Group_1_3_1__0 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:539:6: ( ( rule__GeoJSON__Group_1_3_1__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:540:1: ( rule__GeoJSON__Group_1_3_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_3_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:541:1: ( rule__GeoJSON__Group_1_3_1__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:541:2: rule__GeoJSON__Group_1_3_1__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__0_in_rule__GeoJSON__Alternatives_1_31072);
                    rule__GeoJSON__Group_1_3_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_3_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Alternatives_1_3"


    // $ANTLR start "rule__CRS__Alternatives_8"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:550:1: rule__CRS__Alternatives_8 : ( ( ( rule__CRS__Group_8_0__0 ) ) | ( ( rule__CRS__Group_8_1__0 ) ) );
    public final void rule__CRS__Alternatives_8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:554:1: ( ( ( rule__CRS__Group_8_0__0 ) ) | ( ( rule__CRS__Group_8_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==20) ) {
                alt2=1;
            }
            else if ( (LA2_0==39) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:555:1: ( ( rule__CRS__Group_8_0__0 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:555:1: ( ( rule__CRS__Group_8_0__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:556:1: ( rule__CRS__Group_8_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCRSAccess().getGroup_8_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:557:1: ( rule__CRS__Group_8_0__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:557:2: rule__CRS__Group_8_0__0
                    {
                    pushFollow(FOLLOW_rule__CRS__Group_8_0__0_in_rule__CRS__Alternatives_81105);
                    rule__CRS__Group_8_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCRSAccess().getGroup_8_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:561:6: ( ( rule__CRS__Group_8_1__0 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:561:6: ( ( rule__CRS__Group_8_1__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:562:1: ( rule__CRS__Group_8_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCRSAccess().getGroup_8_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:563:1: ( rule__CRS__Group_8_1__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:563:2: rule__CRS__Group_8_1__0
                    {
                    pushFollow(FOLLOW_rule__CRS__Group_8_1__0_in_rule__CRS__Alternatives_81123);
                    rule__CRS__Group_8_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCRSAccess().getGroup_8_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Alternatives_8"


    // $ANTLR start "rule__Coord__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:572:1: rule__Coord__Alternatives : ( ( ruleCoordList ) | ( ruleCoordArray ) );
    public final void rule__Coord__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:576:1: ( ( ruleCoordList ) | ( ruleCoordArray ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==37) ) {
                int LA3_1 = input.LA(2);

                if ( ((LA3_1>=37 && LA3_1<=38)) ) {
                    alt3=2;
                }
                else if ( (LA3_1==RULE_INT||LA3_1==41) ) {
                    alt3=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:577:1: ( ruleCoordList )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:577:1: ( ruleCoordList )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:578:1: ruleCoordList
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCoordAccess().getCoordListParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_ruleCoordList_in_rule__Coord__Alternatives1156);
                    ruleCoordList();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCoordAccess().getCoordListParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:583:6: ( ruleCoordArray )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:583:6: ( ruleCoordArray )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:584:1: ruleCoordArray
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCoordAccess().getCoordArrayParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleCoordArray_in_rule__Coord__Alternatives1173);
                    ruleCoordArray();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCoordAccess().getCoordArrayParserRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Coord__Alternatives"


    // $ANTLR start "rule__Value__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:594:1: rule__Value__Alternatives : ( ( ( rule__Value__IntValueAssignment_0 ) ) | ( ( rule__Value__NintValueAssignment_1 ) ) | ( ( rule__Value__DoubleValueAssignment_2 ) ) | ( ( rule__Value__StringValueAssignment_3 ) ) | ( ( rule__Value__ArrayValueAssignment_4 ) ) | ( ( rule__Value__StructValueAssignment_5 ) ) );
    public final void rule__Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:598:1: ( ( ( rule__Value__IntValueAssignment_0 ) ) | ( ( rule__Value__NintValueAssignment_1 ) ) | ( ( rule__Value__DoubleValueAssignment_2 ) ) | ( ( rule__Value__StringValueAssignment_3 ) ) | ( ( rule__Value__ArrayValueAssignment_4 ) ) | ( ( rule__Value__StructValueAssignment_5 ) ) )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:599:1: ( ( rule__Value__IntValueAssignment_0 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:599:1: ( ( rule__Value__IntValueAssignment_0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:600:1: ( rule__Value__IntValueAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getIntValueAssignment_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:601:1: ( rule__Value__IntValueAssignment_0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:601:2: rule__Value__IntValueAssignment_0
                    {
                    pushFollow(FOLLOW_rule__Value__IntValueAssignment_0_in_rule__Value__Alternatives1205);
                    rule__Value__IntValueAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getIntValueAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:605:6: ( ( rule__Value__NintValueAssignment_1 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:605:6: ( ( rule__Value__NintValueAssignment_1 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:606:1: ( rule__Value__NintValueAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getNintValueAssignment_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:607:1: ( rule__Value__NintValueAssignment_1 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:607:2: rule__Value__NintValueAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Value__NintValueAssignment_1_in_rule__Value__Alternatives1223);
                    rule__Value__NintValueAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getNintValueAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:611:6: ( ( rule__Value__DoubleValueAssignment_2 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:611:6: ( ( rule__Value__DoubleValueAssignment_2 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:612:1: ( rule__Value__DoubleValueAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getDoubleValueAssignment_2()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:613:1: ( rule__Value__DoubleValueAssignment_2 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:613:2: rule__Value__DoubleValueAssignment_2
                    {
                    pushFollow(FOLLOW_rule__Value__DoubleValueAssignment_2_in_rule__Value__Alternatives1241);
                    rule__Value__DoubleValueAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getDoubleValueAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:617:6: ( ( rule__Value__StringValueAssignment_3 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:617:6: ( ( rule__Value__StringValueAssignment_3 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:618:1: ( rule__Value__StringValueAssignment_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getStringValueAssignment_3()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:619:1: ( rule__Value__StringValueAssignment_3 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:619:2: rule__Value__StringValueAssignment_3
                    {
                    pushFollow(FOLLOW_rule__Value__StringValueAssignment_3_in_rule__Value__Alternatives1259);
                    rule__Value__StringValueAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getStringValueAssignment_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:623:6: ( ( rule__Value__ArrayValueAssignment_4 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:623:6: ( ( rule__Value__ArrayValueAssignment_4 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:624:1: ( rule__Value__ArrayValueAssignment_4 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getArrayValueAssignment_4()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:625:1: ( rule__Value__ArrayValueAssignment_4 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:625:2: rule__Value__ArrayValueAssignment_4
                    {
                    pushFollow(FOLLOW_rule__Value__ArrayValueAssignment_4_in_rule__Value__Alternatives1277);
                    rule__Value__ArrayValueAssignment_4();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getArrayValueAssignment_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:629:6: ( ( rule__Value__StructValueAssignment_5 ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:629:6: ( ( rule__Value__StructValueAssignment_5 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:630:1: ( rule__Value__StructValueAssignment_5 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getStructValueAssignment_5()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:631:1: ( rule__Value__StructValueAssignment_5 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:631:2: rule__Value__StructValueAssignment_5
                    {
                    pushFollow(FOLLOW_rule__Value__StructValueAssignment_5_in_rule__Value__Alternatives1295);
                    rule__Value__StructValueAssignment_5();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getStructValueAssignment_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__Alternatives"


    // $ANTLR start "rule__DOUBLE__Alternatives_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:640:1: rule__DOUBLE__Alternatives_0 : ( ( RULE_INT ) | ( ruleNINT ) );
    public final void rule__DOUBLE__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:644:1: ( ( RULE_INT ) | ( ruleNINT ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_INT) ) {
                alt5=1;
            }
            else if ( (LA5_0==41) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:645:1: ( RULE_INT )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:645:1: ( RULE_INT )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:646:1: RULE_INT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_0_0()); 
                    }
                    match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__DOUBLE__Alternatives_01328); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:651:6: ( ruleNINT )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:651:6: ( ruleNINT )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:652:1: ruleNINT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDOUBLEAccess().getNINTParserRuleCall_0_1()); 
                    }
                    pushFollow(FOLLOW_ruleNINT_in_rule__DOUBLE__Alternatives_01345);
                    ruleNINT();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDOUBLEAccess().getNINTParserRuleCall_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Alternatives_0"


    // $ANTLR start "rule__Type__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:662:1: rule__Type__Alternatives : ( ( ( '\"Point\"' ) ) | ( ( '\"MultiPoint\"' ) ) | ( ( '\"LineString\"' ) ) | ( ( '\"MultiLineString\"' ) ) | ( ( '\"Polygon\"' ) ) | ( ( '\"MultiPolygon\"' ) ) | ( ( '\"GeometryCollection\"' ) ) | ( ( '\"Feature\"' ) ) | ( ( '\"FeatureCollection\"' ) ) );
    public final void rule__Type__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:666:1: ( ( ( '\"Point\"' ) ) | ( ( '\"MultiPoint\"' ) ) | ( ( '\"LineString\"' ) ) | ( ( '\"MultiLineString\"' ) ) | ( ( '\"Polygon\"' ) ) | ( ( '\"MultiPolygon\"' ) ) | ( ( '\"GeometryCollection\"' ) ) | ( ( '\"Feature\"' ) ) | ( ( '\"FeatureCollection\"' ) ) )
            int alt6=9;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt6=1;
                }
                break;
            case 12:
                {
                alt6=2;
                }
                break;
            case 13:
                {
                alt6=3;
                }
                break;
            case 14:
                {
                alt6=4;
                }
                break;
            case 15:
                {
                alt6=5;
                }
                break;
            case 16:
                {
                alt6=6;
                }
                break;
            case 17:
                {
                alt6=7;
                }
                break;
            case 18:
                {
                alt6=8;
                }
                break;
            case 19:
                {
                alt6=9;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:667:1: ( ( '\"Point\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:667:1: ( ( '\"Point\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:668:1: ( '\"Point\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getPOINTEnumLiteralDeclaration_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:669:1: ( '\"Point\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:669:3: '\"Point\"'
                    {
                    match(input,11,FOLLOW_11_in_rule__Type__Alternatives1378); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getPOINTEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:674:6: ( ( '\"MultiPoint\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:674:6: ( ( '\"MultiPoint\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:675:1: ( '\"MultiPoint\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getMULTI_POINTEnumLiteralDeclaration_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:676:1: ( '\"MultiPoint\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:676:3: '\"MultiPoint\"'
                    {
                    match(input,12,FOLLOW_12_in_rule__Type__Alternatives1399); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getMULTI_POINTEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:681:6: ( ( '\"LineString\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:681:6: ( ( '\"LineString\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:682:1: ( '\"LineString\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getLINE_STRINGEnumLiteralDeclaration_2()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:683:1: ( '\"LineString\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:683:3: '\"LineString\"'
                    {
                    match(input,13,FOLLOW_13_in_rule__Type__Alternatives1420); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getLINE_STRINGEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:688:6: ( ( '\"MultiLineString\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:688:6: ( ( '\"MultiLineString\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:689:1: ( '\"MultiLineString\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getMULTI_LINE_STRINGEnumLiteralDeclaration_3()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:690:1: ( '\"MultiLineString\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:690:3: '\"MultiLineString\"'
                    {
                    match(input,14,FOLLOW_14_in_rule__Type__Alternatives1441); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getMULTI_LINE_STRINGEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:695:6: ( ( '\"Polygon\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:695:6: ( ( '\"Polygon\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:696:1: ( '\"Polygon\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getPOLYGONEnumLiteralDeclaration_4()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:697:1: ( '\"Polygon\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:697:3: '\"Polygon\"'
                    {
                    match(input,15,FOLLOW_15_in_rule__Type__Alternatives1462); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getPOLYGONEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:702:6: ( ( '\"MultiPolygon\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:702:6: ( ( '\"MultiPolygon\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:703:1: ( '\"MultiPolygon\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getMULTI_POLYGONEnumLiteralDeclaration_5()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:704:1: ( '\"MultiPolygon\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:704:3: '\"MultiPolygon\"'
                    {
                    match(input,16,FOLLOW_16_in_rule__Type__Alternatives1483); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getMULTI_POLYGONEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:709:6: ( ( '\"GeometryCollection\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:709:6: ( ( '\"GeometryCollection\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:710:1: ( '\"GeometryCollection\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getGEOMETRY_COLLECTIONEnumLiteralDeclaration_6()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:711:1: ( '\"GeometryCollection\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:711:3: '\"GeometryCollection\"'
                    {
                    match(input,17,FOLLOW_17_in_rule__Type__Alternatives1504); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getGEOMETRY_COLLECTIONEnumLiteralDeclaration_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:716:6: ( ( '\"Feature\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:716:6: ( ( '\"Feature\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:717:1: ( '\"Feature\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getFEATUREEnumLiteralDeclaration_7()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:718:1: ( '\"Feature\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:718:3: '\"Feature\"'
                    {
                    match(input,18,FOLLOW_18_in_rule__Type__Alternatives1525); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getFEATUREEnumLiteralDeclaration_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:723:6: ( ( '\"FeatureCollection\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:723:6: ( ( '\"FeatureCollection\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:724:1: ( '\"FeatureCollection\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTypeAccess().getFEATURE_COLLECTIONEnumLiteralDeclaration_8()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:725:1: ( '\"FeatureCollection\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:725:3: '\"FeatureCollection\"'
                    {
                    match(input,19,FOLLOW_19_in_rule__Type__Alternatives1546); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTypeAccess().getFEATURE_COLLECTIONEnumLiteralDeclaration_8()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Type__Alternatives"


    // $ANTLR start "rule__CRSType__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:735:1: rule__CRSType__Alternatives : ( ( ( '\"name\"' ) ) | ( ( '\"link\"' ) ) );
    public final void rule__CRSType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:739:1: ( ( ( '\"name\"' ) ) | ( ( '\"link\"' ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            else if ( (LA7_0==21) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:740:1: ( ( '\"name\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:740:1: ( ( '\"name\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:741:1: ( '\"name\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCRSTypeAccess().getNAMEEnumLiteralDeclaration_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:742:1: ( '\"name\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:742:3: '\"name\"'
                    {
                    match(input,20,FOLLOW_20_in_rule__CRSType__Alternatives1582); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCRSTypeAccess().getNAMEEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:747:6: ( ( '\"link\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:747:6: ( ( '\"link\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:748:1: ( '\"link\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCRSTypeAccess().getLINKEDEnumLiteralDeclaration_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:749:1: ( '\"link\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:749:3: '\"link\"'
                    {
                    match(input,21,FOLLOW_21_in_rule__CRSType__Alternatives1603); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCRSTypeAccess().getLINKEDEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRSType__Alternatives"


    // $ANTLR start "rule__LinkCRSType__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:759:1: rule__LinkCRSType__Alternatives : ( ( ( '\"proj4\"' ) ) | ( ( '\"ogcwkt\"' ) ) | ( ( '\"esriwkt\"' ) ) );
    public final void rule__LinkCRSType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:763:1: ( ( ( '\"proj4\"' ) ) | ( ( '\"ogcwkt\"' ) ) | ( ( '\"esriwkt\"' ) ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt8=1;
                }
                break;
            case 23:
                {
                alt8=2;
                }
                break;
            case 24:
                {
                alt8=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:764:1: ( ( '\"proj4\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:764:1: ( ( '\"proj4\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:765:1: ( '\"proj4\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLinkCRSTypeAccess().getPROJ4EnumLiteralDeclaration_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:766:1: ( '\"proj4\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:766:3: '\"proj4\"'
                    {
                    match(input,22,FOLLOW_22_in_rule__LinkCRSType__Alternatives1639); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLinkCRSTypeAccess().getPROJ4EnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:771:6: ( ( '\"ogcwkt\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:771:6: ( ( '\"ogcwkt\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:772:1: ( '\"ogcwkt\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLinkCRSTypeAccess().getOGCWKTEnumLiteralDeclaration_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:773:1: ( '\"ogcwkt\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:773:3: '\"ogcwkt\"'
                    {
                    match(input,23,FOLLOW_23_in_rule__LinkCRSType__Alternatives1660); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLinkCRSTypeAccess().getOGCWKTEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:778:6: ( ( '\"esriwkt\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:778:6: ( ( '\"esriwkt\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:779:1: ( '\"esriwkt\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLinkCRSTypeAccess().getESRIWKTEnumLiteralDeclaration_2()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:780:1: ( '\"esriwkt\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:780:3: '\"esriwkt\"'
                    {
                    match(input,24,FOLLOW_24_in_rule__LinkCRSType__Alternatives1681); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLinkCRSTypeAccess().getESRIWKTEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LinkCRSType__Alternatives"


    // $ANTLR start "rule__GeometryType__Alternatives"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:790:1: rule__GeometryType__Alternatives : ( ( ( '\"Point\"' ) ) | ( ( '\"MultiPoint\"' ) ) | ( ( '\"LineString\"' ) ) | ( ( '\"MultiLineString\"' ) ) | ( ( '\"Polygon\"' ) ) | ( ( '\"MultiPolygon\"' ) ) | ( ( '\"GeometryCollection\"' ) ) );
    public final void rule__GeometryType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:794:1: ( ( ( '\"Point\"' ) ) | ( ( '\"MultiPoint\"' ) ) | ( ( '\"LineString\"' ) ) | ( ( '\"MultiLineString\"' ) ) | ( ( '\"Polygon\"' ) ) | ( ( '\"MultiPolygon\"' ) ) | ( ( '\"GeometryCollection\"' ) ) )
            int alt9=7;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt9=1;
                }
                break;
            case 12:
                {
                alt9=2;
                }
                break;
            case 13:
                {
                alt9=3;
                }
                break;
            case 14:
                {
                alt9=4;
                }
                break;
            case 15:
                {
                alt9=5;
                }
                break;
            case 16:
                {
                alt9=6;
                }
                break;
            case 17:
                {
                alt9=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:795:1: ( ( '\"Point\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:795:1: ( ( '\"Point\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:796:1: ( '\"Point\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getPOINTEnumLiteralDeclaration_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:797:1: ( '\"Point\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:797:3: '\"Point\"'
                    {
                    match(input,11,FOLLOW_11_in_rule__GeometryType__Alternatives1717); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getPOINTEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:802:6: ( ( '\"MultiPoint\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:802:6: ( ( '\"MultiPoint\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:803:1: ( '\"MultiPoint\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getMULTI_POINTEnumLiteralDeclaration_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:804:1: ( '\"MultiPoint\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:804:3: '\"MultiPoint\"'
                    {
                    match(input,12,FOLLOW_12_in_rule__GeometryType__Alternatives1738); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getMULTI_POINTEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:809:6: ( ( '\"LineString\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:809:6: ( ( '\"LineString\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:810:1: ( '\"LineString\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getLINESTRINGEnumLiteralDeclaration_2()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:811:1: ( '\"LineString\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:811:3: '\"LineString\"'
                    {
                    match(input,13,FOLLOW_13_in_rule__GeometryType__Alternatives1759); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getLINESTRINGEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:816:6: ( ( '\"MultiLineString\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:816:6: ( ( '\"MultiLineString\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:817:1: ( '\"MultiLineString\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getMULTI_LINE_STRINGEnumLiteralDeclaration_3()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:818:1: ( '\"MultiLineString\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:818:3: '\"MultiLineString\"'
                    {
                    match(input,14,FOLLOW_14_in_rule__GeometryType__Alternatives1780); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getMULTI_LINE_STRINGEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:823:6: ( ( '\"Polygon\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:823:6: ( ( '\"Polygon\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:824:1: ( '\"Polygon\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getPOLYGONEnumLiteralDeclaration_4()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:825:1: ( '\"Polygon\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:825:3: '\"Polygon\"'
                    {
                    match(input,15,FOLLOW_15_in_rule__GeometryType__Alternatives1801); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getPOLYGONEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:830:6: ( ( '\"MultiPolygon\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:830:6: ( ( '\"MultiPolygon\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:831:1: ( '\"MultiPolygon\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getMULTI_POLYGONEnumLiteralDeclaration_5()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:832:1: ( '\"MultiPolygon\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:832:3: '\"MultiPolygon\"'
                    {
                    match(input,16,FOLLOW_16_in_rule__GeometryType__Alternatives1822); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getMULTI_POLYGONEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:837:6: ( ( '\"GeometryCollection\"' ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:837:6: ( ( '\"GeometryCollection\"' ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:838:1: ( '\"GeometryCollection\"' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeometryTypeAccess().getGEOMETRY_COLLECTIONEnumLiteralDeclaration_6()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:839:1: ( '\"GeometryCollection\"' )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:839:3: '\"GeometryCollection\"'
                    {
                    match(input,17,FOLLOW_17_in_rule__GeometryType__Alternatives1843); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeometryTypeAccess().getGEOMETRY_COLLECTIONEnumLiteralDeclaration_6()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeometryType__Alternatives"


    // $ANTLR start "rule__GeoJSON__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:851:1: rule__GeoJSON__Group__0 : rule__GeoJSON__Group__0__Impl rule__GeoJSON__Group__1 ;
    public final void rule__GeoJSON__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:855:1: ( rule__GeoJSON__Group__0__Impl rule__GeoJSON__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:856:2: rule__GeoJSON__Group__0__Impl rule__GeoJSON__Group__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group__0__Impl_in_rule__GeoJSON__Group__01876);
            rule__GeoJSON__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group__1_in_rule__GeoJSON__Group__01879);
            rule__GeoJSON__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__0"


    // $ANTLR start "rule__GeoJSON__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:863:1: rule__GeoJSON__Group__0__Impl : ( '{' ) ;
    public final void rule__GeoJSON__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:867:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:868:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:868:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:869:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getLeftCurlyBracketKeyword_0()); 
            }
            match(input,25,FOLLOW_25_in_rule__GeoJSON__Group__0__Impl1907); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getLeftCurlyBracketKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:882:1: rule__GeoJSON__Group__1 : rule__GeoJSON__Group__1__Impl rule__GeoJSON__Group__2 ;
    public final void rule__GeoJSON__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:886:1: ( rule__GeoJSON__Group__1__Impl rule__GeoJSON__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:887:2: rule__GeoJSON__Group__1__Impl rule__GeoJSON__Group__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group__1__Impl_in_rule__GeoJSON__Group__11938);
            rule__GeoJSON__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group__2_in_rule__GeoJSON__Group__11941);
            rule__GeoJSON__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__1"


    // $ANTLR start "rule__GeoJSON__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:894:1: rule__GeoJSON__Group__1__Impl : ( ( rule__GeoJSON__UnorderedGroup_1 ) ) ;
    public final void rule__GeoJSON__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:898:1: ( ( ( rule__GeoJSON__UnorderedGroup_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:899:1: ( ( rule__GeoJSON__UnorderedGroup_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:899:1: ( ( rule__GeoJSON__UnorderedGroup_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:900:1: ( rule__GeoJSON__UnorderedGroup_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:901:1: ( rule__GeoJSON__UnorderedGroup_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:901:2: rule__GeoJSON__UnorderedGroup_1
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1_in_rule__GeoJSON__Group__1__Impl1968);
            rule__GeoJSON__UnorderedGroup_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:911:1: rule__GeoJSON__Group__2 : rule__GeoJSON__Group__2__Impl ;
    public final void rule__GeoJSON__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:915:1: ( rule__GeoJSON__Group__2__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:916:2: rule__GeoJSON__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group__2__Impl_in_rule__GeoJSON__Group__21998);
            rule__GeoJSON__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__2"


    // $ANTLR start "rule__GeoJSON__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:922:1: rule__GeoJSON__Group__2__Impl : ( '}' ) ;
    public final void rule__GeoJSON__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:926:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:927:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:927:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:928:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getRightCurlyBracketKeyword_2()); 
            }
            match(input,26,FOLLOW_26_in_rule__GeoJSON__Group__2__Impl2026); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getRightCurlyBracketKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_0__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:947:1: rule__GeoJSON__Group_1_0__0 : rule__GeoJSON__Group_1_0__0__Impl rule__GeoJSON__Group_1_0__1 ;
    public final void rule__GeoJSON__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:951:1: ( rule__GeoJSON__Group_1_0__0__Impl rule__GeoJSON__Group_1_0__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:952:2: rule__GeoJSON__Group_1_0__0__Impl rule__GeoJSON__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__0__Impl_in_rule__GeoJSON__Group_1_0__02063);
            rule__GeoJSON__Group_1_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__1_in_rule__GeoJSON__Group_1_0__02066);
            rule__GeoJSON__Group_1_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__0"


    // $ANTLR start "rule__GeoJSON__Group_1_0__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:959:1: rule__GeoJSON__Group_1_0__0__Impl : ( '\"type\"' ) ;
    public final void rule__GeoJSON__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:963:1: ( ( '\"type\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:964:1: ( '\"type\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:964:1: ( '\"type\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:965:1: '\"type\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getTypeKeyword_1_0_0()); 
            }
            match(input,27,FOLLOW_27_in_rule__GeoJSON__Group_1_0__0__Impl2094); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getTypeKeyword_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_0__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:978:1: rule__GeoJSON__Group_1_0__1 : rule__GeoJSON__Group_1_0__1__Impl rule__GeoJSON__Group_1_0__2 ;
    public final void rule__GeoJSON__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:982:1: ( rule__GeoJSON__Group_1_0__1__Impl rule__GeoJSON__Group_1_0__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:983:2: rule__GeoJSON__Group_1_0__1__Impl rule__GeoJSON__Group_1_0__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__1__Impl_in_rule__GeoJSON__Group_1_0__12125);
            rule__GeoJSON__Group_1_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__2_in_rule__GeoJSON__Group_1_0__12128);
            rule__GeoJSON__Group_1_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__1"


    // $ANTLR start "rule__GeoJSON__Group_1_0__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:990:1: rule__GeoJSON__Group_1_0__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:994:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:995:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:995:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:996:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_0_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_0__1__Impl2156); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_0__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1009:1: rule__GeoJSON__Group_1_0__2 : rule__GeoJSON__Group_1_0__2__Impl rule__GeoJSON__Group_1_0__3 ;
    public final void rule__GeoJSON__Group_1_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1013:1: ( rule__GeoJSON__Group_1_0__2__Impl rule__GeoJSON__Group_1_0__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1014:2: rule__GeoJSON__Group_1_0__2__Impl rule__GeoJSON__Group_1_0__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__2__Impl_in_rule__GeoJSON__Group_1_0__22187);
            rule__GeoJSON__Group_1_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__3_in_rule__GeoJSON__Group_1_0__22190);
            rule__GeoJSON__Group_1_0__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__2"


    // $ANTLR start "rule__GeoJSON__Group_1_0__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1021:1: rule__GeoJSON__Group_1_0__2__Impl : ( ( rule__GeoJSON__TypeAssignment_1_0_2 ) ) ;
    public final void rule__GeoJSON__Group_1_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1025:1: ( ( ( rule__GeoJSON__TypeAssignment_1_0_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1026:1: ( ( rule__GeoJSON__TypeAssignment_1_0_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1026:1: ( ( rule__GeoJSON__TypeAssignment_1_0_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1027:1: ( rule__GeoJSON__TypeAssignment_1_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getTypeAssignment_1_0_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1028:1: ( rule__GeoJSON__TypeAssignment_1_0_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1028:2: rule__GeoJSON__TypeAssignment_1_0_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__TypeAssignment_1_0_2_in_rule__GeoJSON__Group_1_0__2__Impl2217);
            rule__GeoJSON__TypeAssignment_1_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getTypeAssignment_1_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_0__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1038:1: rule__GeoJSON__Group_1_0__3 : rule__GeoJSON__Group_1_0__3__Impl ;
    public final void rule__GeoJSON__Group_1_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1042:1: ( rule__GeoJSON__Group_1_0__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1043:2: rule__GeoJSON__Group_1_0__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__3__Impl_in_rule__GeoJSON__Group_1_0__32247);
            rule__GeoJSON__Group_1_0__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__3"


    // $ANTLR start "rule__GeoJSON__Group_1_0__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1049:1: rule__GeoJSON__Group_1_0__3__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1053:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1054:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1054:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1055:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_0_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1056:1: ( ',' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==29) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1057:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_0__3__Impl2276); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_0_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_0__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1076:1: rule__GeoJSON__Group_1_1__0 : rule__GeoJSON__Group_1_1__0__Impl rule__GeoJSON__Group_1_1__1 ;
    public final void rule__GeoJSON__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1080:1: ( rule__GeoJSON__Group_1_1__0__Impl rule__GeoJSON__Group_1_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1081:2: rule__GeoJSON__Group_1_1__0__Impl rule__GeoJSON__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__0__Impl_in_rule__GeoJSON__Group_1_1__02317);
            rule__GeoJSON__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__1_in_rule__GeoJSON__Group_1_1__02320);
            rule__GeoJSON__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__0"


    // $ANTLR start "rule__GeoJSON__Group_1_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1088:1: rule__GeoJSON__Group_1_1__0__Impl : ( '\"crs\"' ) ;
    public final void rule__GeoJSON__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1092:1: ( ( '\"crs\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1093:1: ( '\"crs\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1093:1: ( '\"crs\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1094:1: '\"crs\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCrsKeyword_1_1_0()); 
            }
            match(input,30,FOLLOW_30_in_rule__GeoJSON__Group_1_1__0__Impl2348); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCrsKeyword_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1107:1: rule__GeoJSON__Group_1_1__1 : rule__GeoJSON__Group_1_1__1__Impl rule__GeoJSON__Group_1_1__2 ;
    public final void rule__GeoJSON__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1111:1: ( rule__GeoJSON__Group_1_1__1__Impl rule__GeoJSON__Group_1_1__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1112:2: rule__GeoJSON__Group_1_1__1__Impl rule__GeoJSON__Group_1_1__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__1__Impl_in_rule__GeoJSON__Group_1_1__12379);
            rule__GeoJSON__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__2_in_rule__GeoJSON__Group_1_1__12382);
            rule__GeoJSON__Group_1_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__1"


    // $ANTLR start "rule__GeoJSON__Group_1_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1119:1: rule__GeoJSON__Group_1_1__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1123:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1124:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1124:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1125:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_1_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_1__1__Impl2410); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_1__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1138:1: rule__GeoJSON__Group_1_1__2 : rule__GeoJSON__Group_1_1__2__Impl rule__GeoJSON__Group_1_1__3 ;
    public final void rule__GeoJSON__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1142:1: ( rule__GeoJSON__Group_1_1__2__Impl rule__GeoJSON__Group_1_1__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1143:2: rule__GeoJSON__Group_1_1__2__Impl rule__GeoJSON__Group_1_1__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__2__Impl_in_rule__GeoJSON__Group_1_1__22441);
            rule__GeoJSON__Group_1_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__3_in_rule__GeoJSON__Group_1_1__22444);
            rule__GeoJSON__Group_1_1__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__2"


    // $ANTLR start "rule__GeoJSON__Group_1_1__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1150:1: rule__GeoJSON__Group_1_1__2__Impl : ( ( rule__GeoJSON__CrsAssignment_1_1_2 ) ) ;
    public final void rule__GeoJSON__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1154:1: ( ( ( rule__GeoJSON__CrsAssignment_1_1_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1155:1: ( ( rule__GeoJSON__CrsAssignment_1_1_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1155:1: ( ( rule__GeoJSON__CrsAssignment_1_1_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1156:1: ( rule__GeoJSON__CrsAssignment_1_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCrsAssignment_1_1_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1157:1: ( rule__GeoJSON__CrsAssignment_1_1_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1157:2: rule__GeoJSON__CrsAssignment_1_1_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__CrsAssignment_1_1_2_in_rule__GeoJSON__Group_1_1__2__Impl2471);
            rule__GeoJSON__CrsAssignment_1_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCrsAssignment_1_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_1__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1167:1: rule__GeoJSON__Group_1_1__3 : rule__GeoJSON__Group_1_1__3__Impl ;
    public final void rule__GeoJSON__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1171:1: ( rule__GeoJSON__Group_1_1__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1172:2: rule__GeoJSON__Group_1_1__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__3__Impl_in_rule__GeoJSON__Group_1_1__32501);
            rule__GeoJSON__Group_1_1__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__3"


    // $ANTLR start "rule__GeoJSON__Group_1_1__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1178:1: rule__GeoJSON__Group_1_1__3__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1182:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1183:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1183:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1184:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_1_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1185:1: ( ',' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==29) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1186:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_1__3__Impl2530); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_1_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_1__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1205:1: rule__GeoJSON__Group_1_2__0 : rule__GeoJSON__Group_1_2__0__Impl rule__GeoJSON__Group_1_2__1 ;
    public final void rule__GeoJSON__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1209:1: ( rule__GeoJSON__Group_1_2__0__Impl rule__GeoJSON__Group_1_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1210:2: rule__GeoJSON__Group_1_2__0__Impl rule__GeoJSON__Group_1_2__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__0__Impl_in_rule__GeoJSON__Group_1_2__02571);
            rule__GeoJSON__Group_1_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__1_in_rule__GeoJSON__Group_1_2__02574);
            rule__GeoJSON__Group_1_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__0"


    // $ANTLR start "rule__GeoJSON__Group_1_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1217:1: rule__GeoJSON__Group_1_2__0__Impl : ( '\"bbox\"' ) ;
    public final void rule__GeoJSON__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1221:1: ( ( '\"bbox\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1222:1: ( '\"bbox\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1222:1: ( '\"bbox\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1223:1: '\"bbox\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getBboxKeyword_1_2_0()); 
            }
            match(input,31,FOLLOW_31_in_rule__GeoJSON__Group_1_2__0__Impl2602); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getBboxKeyword_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1236:1: rule__GeoJSON__Group_1_2__1 : rule__GeoJSON__Group_1_2__1__Impl rule__GeoJSON__Group_1_2__2 ;
    public final void rule__GeoJSON__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1240:1: ( rule__GeoJSON__Group_1_2__1__Impl rule__GeoJSON__Group_1_2__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1241:2: rule__GeoJSON__Group_1_2__1__Impl rule__GeoJSON__Group_1_2__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__1__Impl_in_rule__GeoJSON__Group_1_2__12633);
            rule__GeoJSON__Group_1_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__2_in_rule__GeoJSON__Group_1_2__12636);
            rule__GeoJSON__Group_1_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__1"


    // $ANTLR start "rule__GeoJSON__Group_1_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1248:1: rule__GeoJSON__Group_1_2__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1252:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1253:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1253:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1254:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_2_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_2__1__Impl2664); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_2__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1267:1: rule__GeoJSON__Group_1_2__2 : rule__GeoJSON__Group_1_2__2__Impl rule__GeoJSON__Group_1_2__3 ;
    public final void rule__GeoJSON__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1271:1: ( rule__GeoJSON__Group_1_2__2__Impl rule__GeoJSON__Group_1_2__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1272:2: rule__GeoJSON__Group_1_2__2__Impl rule__GeoJSON__Group_1_2__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__2__Impl_in_rule__GeoJSON__Group_1_2__22695);
            rule__GeoJSON__Group_1_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__3_in_rule__GeoJSON__Group_1_2__22698);
            rule__GeoJSON__Group_1_2__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__2"


    // $ANTLR start "rule__GeoJSON__Group_1_2__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1279:1: rule__GeoJSON__Group_1_2__2__Impl : ( ( rule__GeoJSON__BboxAssignment_1_2_2 ) ) ;
    public final void rule__GeoJSON__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1283:1: ( ( ( rule__GeoJSON__BboxAssignment_1_2_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1284:1: ( ( rule__GeoJSON__BboxAssignment_1_2_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1284:1: ( ( rule__GeoJSON__BboxAssignment_1_2_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1285:1: ( rule__GeoJSON__BboxAssignment_1_2_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getBboxAssignment_1_2_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1286:1: ( rule__GeoJSON__BboxAssignment_1_2_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1286:2: rule__GeoJSON__BboxAssignment_1_2_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__BboxAssignment_1_2_2_in_rule__GeoJSON__Group_1_2__2__Impl2725);
            rule__GeoJSON__BboxAssignment_1_2_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getBboxAssignment_1_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_2__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1296:1: rule__GeoJSON__Group_1_2__3 : rule__GeoJSON__Group_1_2__3__Impl ;
    public final void rule__GeoJSON__Group_1_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1300:1: ( rule__GeoJSON__Group_1_2__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1301:2: rule__GeoJSON__Group_1_2__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__3__Impl_in_rule__GeoJSON__Group_1_2__32755);
            rule__GeoJSON__Group_1_2__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__3"


    // $ANTLR start "rule__GeoJSON__Group_1_2__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1307:1: rule__GeoJSON__Group_1_2__3__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1311:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1312:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1312:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1313:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_2_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1314:1: ( ',' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==29) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1315:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_2__3__Impl2784); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_2_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_2__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1334:1: rule__GeoJSON__Group_1_3_0__0 : rule__GeoJSON__Group_1_3_0__0__Impl rule__GeoJSON__Group_1_3_0__1 ;
    public final void rule__GeoJSON__Group_1_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1338:1: ( rule__GeoJSON__Group_1_3_0__0__Impl rule__GeoJSON__Group_1_3_0__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1339:2: rule__GeoJSON__Group_1_3_0__0__Impl rule__GeoJSON__Group_1_3_0__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__0__Impl_in_rule__GeoJSON__Group_1_3_0__02825);
            rule__GeoJSON__Group_1_3_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__1_in_rule__GeoJSON__Group_1_3_0__02828);
            rule__GeoJSON__Group_1_3_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__0"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1346:1: rule__GeoJSON__Group_1_3_0__0__Impl : ( '\"geometry\"' ) ;
    public final void rule__GeoJSON__Group_1_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1350:1: ( ( '\"geometry\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1351:1: ( '\"geometry\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1351:1: ( '\"geometry\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1352:1: '\"geometry\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryKeyword_1_3_0_0()); 
            }
            match(input,32,FOLLOW_32_in_rule__GeoJSON__Group_1_3_0__0__Impl2856); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryKeyword_1_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1365:1: rule__GeoJSON__Group_1_3_0__1 : rule__GeoJSON__Group_1_3_0__1__Impl rule__GeoJSON__Group_1_3_0__2 ;
    public final void rule__GeoJSON__Group_1_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1369:1: ( rule__GeoJSON__Group_1_3_0__1__Impl rule__GeoJSON__Group_1_3_0__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1370:2: rule__GeoJSON__Group_1_3_0__1__Impl rule__GeoJSON__Group_1_3_0__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__1__Impl_in_rule__GeoJSON__Group_1_3_0__12887);
            rule__GeoJSON__Group_1_3_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__2_in_rule__GeoJSON__Group_1_3_0__12890);
            rule__GeoJSON__Group_1_3_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__1"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1377:1: rule__GeoJSON__Group_1_3_0__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1381:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1382:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1382:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1383:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_3_0_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_3_0__1__Impl2918); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_3_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1396:1: rule__GeoJSON__Group_1_3_0__2 : rule__GeoJSON__Group_1_3_0__2__Impl rule__GeoJSON__Group_1_3_0__3 ;
    public final void rule__GeoJSON__Group_1_3_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1400:1: ( rule__GeoJSON__Group_1_3_0__2__Impl rule__GeoJSON__Group_1_3_0__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1401:2: rule__GeoJSON__Group_1_3_0__2__Impl rule__GeoJSON__Group_1_3_0__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__2__Impl_in_rule__GeoJSON__Group_1_3_0__22949);
            rule__GeoJSON__Group_1_3_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__3_in_rule__GeoJSON__Group_1_3_0__22952);
            rule__GeoJSON__Group_1_3_0__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__2"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1408:1: rule__GeoJSON__Group_1_3_0__2__Impl : ( ( rule__GeoJSON__GeometryAssignment_1_3_0_2 ) ) ;
    public final void rule__GeoJSON__Group_1_3_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1412:1: ( ( ( rule__GeoJSON__GeometryAssignment_1_3_0_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1413:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_0_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1413:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_0_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1414:1: ( rule__GeoJSON__GeometryAssignment_1_3_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_0_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1415:1: ( rule__GeoJSON__GeometryAssignment_1_3_0_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1415:2: rule__GeoJSON__GeometryAssignment_1_3_0_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_0_2_in_rule__GeoJSON__Group_1_3_0__2__Impl2979);
            rule__GeoJSON__GeometryAssignment_1_3_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1425:1: rule__GeoJSON__Group_1_3_0__3 : rule__GeoJSON__Group_1_3_0__3__Impl ;
    public final void rule__GeoJSON__Group_1_3_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1429:1: ( rule__GeoJSON__Group_1_3_0__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1430:2: rule__GeoJSON__Group_1_3_0__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_0__3__Impl_in_rule__GeoJSON__Group_1_3_0__33009);
            rule__GeoJSON__Group_1_3_0__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__3"


    // $ANTLR start "rule__GeoJSON__Group_1_3_0__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1436:1: rule__GeoJSON__Group_1_3_0__3__Impl : ( ',' ) ;
    public final void rule__GeoJSON__Group_1_3_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1440:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1441:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1441:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1442:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_0_3()); 
            }
            match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_3_0__3__Impl3037); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_0_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_0__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1463:1: rule__GeoJSON__Group_1_3_1__0 : rule__GeoJSON__Group_1_3_1__0__Impl rule__GeoJSON__Group_1_3_1__1 ;
    public final void rule__GeoJSON__Group_1_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1467:1: ( rule__GeoJSON__Group_1_3_1__0__Impl rule__GeoJSON__Group_1_3_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1468:2: rule__GeoJSON__Group_1_3_1__0__Impl rule__GeoJSON__Group_1_3_1__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__0__Impl_in_rule__GeoJSON__Group_1_3_1__03076);
            rule__GeoJSON__Group_1_3_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__1_in_rule__GeoJSON__Group_1_3_1__03079);
            rule__GeoJSON__Group_1_3_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__0"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1475:1: rule__GeoJSON__Group_1_3_1__0__Impl : ( '\"geometries\"' ) ;
    public final void rule__GeoJSON__Group_1_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1479:1: ( ( '\"geometries\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1480:1: ( '\"geometries\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1480:1: ( '\"geometries\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1481:1: '\"geometries\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometriesKeyword_1_3_1_0()); 
            }
            match(input,33,FOLLOW_33_in_rule__GeoJSON__Group_1_3_1__0__Impl3107); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometriesKeyword_1_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1494:1: rule__GeoJSON__Group_1_3_1__1 : rule__GeoJSON__Group_1_3_1__1__Impl rule__GeoJSON__Group_1_3_1__2 ;
    public final void rule__GeoJSON__Group_1_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1498:1: ( rule__GeoJSON__Group_1_3_1__1__Impl rule__GeoJSON__Group_1_3_1__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1499:2: rule__GeoJSON__Group_1_3_1__1__Impl rule__GeoJSON__Group_1_3_1__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__1__Impl_in_rule__GeoJSON__Group_1_3_1__13138);
            rule__GeoJSON__Group_1_3_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__2_in_rule__GeoJSON__Group_1_3_1__13141);
            rule__GeoJSON__Group_1_3_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__1"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1506:1: rule__GeoJSON__Group_1_3_1__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1510:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1511:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1511:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1512:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_3_1_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_3_1__1__Impl3169); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_3_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1525:1: rule__GeoJSON__Group_1_3_1__2 : rule__GeoJSON__Group_1_3_1__2__Impl rule__GeoJSON__Group_1_3_1__3 ;
    public final void rule__GeoJSON__Group_1_3_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1529:1: ( rule__GeoJSON__Group_1_3_1__2__Impl rule__GeoJSON__Group_1_3_1__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1530:2: rule__GeoJSON__Group_1_3_1__2__Impl rule__GeoJSON__Group_1_3_1__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__2__Impl_in_rule__GeoJSON__Group_1_3_1__23200);
            rule__GeoJSON__Group_1_3_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__3_in_rule__GeoJSON__Group_1_3_1__23203);
            rule__GeoJSON__Group_1_3_1__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__2"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1537:1: rule__GeoJSON__Group_1_3_1__2__Impl : ( ( rule__GeoJSON__GeometryAssignment_1_3_1_2 ) ) ;
    public final void rule__GeoJSON__Group_1_3_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1541:1: ( ( ( rule__GeoJSON__GeometryAssignment_1_3_1_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1542:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_1_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1542:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_1_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1543:1: ( rule__GeoJSON__GeometryAssignment_1_3_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1544:1: ( rule__GeoJSON__GeometryAssignment_1_3_1_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1544:2: rule__GeoJSON__GeometryAssignment_1_3_1_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_1_2_in_rule__GeoJSON__Group_1_3_1__2__Impl3230);
            rule__GeoJSON__GeometryAssignment_1_3_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1554:1: rule__GeoJSON__Group_1_3_1__3 : rule__GeoJSON__Group_1_3_1__3__Impl rule__GeoJSON__Group_1_3_1__4 ;
    public final void rule__GeoJSON__Group_1_3_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1558:1: ( rule__GeoJSON__Group_1_3_1__3__Impl rule__GeoJSON__Group_1_3_1__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1559:2: rule__GeoJSON__Group_1_3_1__3__Impl rule__GeoJSON__Group_1_3_1__4
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__3__Impl_in_rule__GeoJSON__Group_1_3_1__33260);
            rule__GeoJSON__Group_1_3_1__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__4_in_rule__GeoJSON__Group_1_3_1__33263);
            rule__GeoJSON__Group_1_3_1__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__3"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1566:1: rule__GeoJSON__Group_1_3_1__3__Impl : ( ( ( rule__GeoJSON__Group_1_3_1_3__0 ) ) ( ( rule__GeoJSON__Group_1_3_1_3__0 )* ) ) ;
    public final void rule__GeoJSON__Group_1_3_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1570:1: ( ( ( ( rule__GeoJSON__Group_1_3_1_3__0 ) ) ( ( rule__GeoJSON__Group_1_3_1_3__0 )* ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1571:1: ( ( ( rule__GeoJSON__Group_1_3_1_3__0 ) ) ( ( rule__GeoJSON__Group_1_3_1_3__0 )* ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1571:1: ( ( ( rule__GeoJSON__Group_1_3_1_3__0 ) ) ( ( rule__GeoJSON__Group_1_3_1_3__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1572:1: ( ( rule__GeoJSON__Group_1_3_1_3__0 ) ) ( ( rule__GeoJSON__Group_1_3_1_3__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1572:1: ( ( rule__GeoJSON__Group_1_3_1_3__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1573:1: ( rule__GeoJSON__Group_1_3_1_3__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGroup_1_3_1_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1574:1: ( rule__GeoJSON__Group_1_3_1_3__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1574:2: rule__GeoJSON__Group_1_3_1_3__0
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1_3__0_in_rule__GeoJSON__Group_1_3_1__3__Impl3292);
            rule__GeoJSON__Group_1_3_1_3__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGroup_1_3_1_3()); 
            }

            }

            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1577:1: ( ( rule__GeoJSON__Group_1_3_1_3__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1578:1: ( rule__GeoJSON__Group_1_3_1_3__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGroup_1_3_1_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1579:1: ( rule__GeoJSON__Group_1_3_1_3__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==29) ) {
                    int LA13_1 = input.LA(2);

                    if ( (LA13_1==25) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1579:2: rule__GeoJSON__Group_1_3_1_3__0
            	    {
            	    pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1_3__0_in_rule__GeoJSON__Group_1_3_1__3__Impl3304);
            	    rule__GeoJSON__Group_1_3_1_3__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGroup_1_3_1_3()); 
            }

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1590:1: rule__GeoJSON__Group_1_3_1__4 : rule__GeoJSON__Group_1_3_1__4__Impl ;
    public final void rule__GeoJSON__Group_1_3_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1594:1: ( rule__GeoJSON__Group_1_3_1__4__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1595:2: rule__GeoJSON__Group_1_3_1__4__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1__4__Impl_in_rule__GeoJSON__Group_1_3_1__43337);
            rule__GeoJSON__Group_1_3_1__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__4"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1601:1: rule__GeoJSON__Group_1_3_1__4__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_3_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1605:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1606:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1606:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1607:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_1_4()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1608:1: ( ',' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==29) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1609:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_3_1__4__Impl3366); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_1_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1__4__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1_3__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1630:1: rule__GeoJSON__Group_1_3_1_3__0 : rule__GeoJSON__Group_1_3_1_3__0__Impl rule__GeoJSON__Group_1_3_1_3__1 ;
    public final void rule__GeoJSON__Group_1_3_1_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1634:1: ( rule__GeoJSON__Group_1_3_1_3__0__Impl rule__GeoJSON__Group_1_3_1_3__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1635:2: rule__GeoJSON__Group_1_3_1_3__0__Impl rule__GeoJSON__Group_1_3_1_3__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1_3__0__Impl_in_rule__GeoJSON__Group_1_3_1_3__03409);
            rule__GeoJSON__Group_1_3_1_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1_3__1_in_rule__GeoJSON__Group_1_3_1_3__03412);
            rule__GeoJSON__Group_1_3_1_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1_3__0"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1_3__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1642:1: rule__GeoJSON__Group_1_3_1_3__0__Impl : ( ',' ) ;
    public final void rule__GeoJSON__Group_1_3_1_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1646:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1647:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1647:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1648:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_1_3_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_3_1_3__0__Impl3440); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_3_1_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1_3__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1_3__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1661:1: rule__GeoJSON__Group_1_3_1_3__1 : rule__GeoJSON__Group_1_3_1_3__1__Impl ;
    public final void rule__GeoJSON__Group_1_3_1_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1665:1: ( rule__GeoJSON__Group_1_3_1_3__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1666:2: rule__GeoJSON__Group_1_3_1_3__1__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_3_1_3__1__Impl_in_rule__GeoJSON__Group_1_3_1_3__13471);
            rule__GeoJSON__Group_1_3_1_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1_3__1"


    // $ANTLR start "rule__GeoJSON__Group_1_3_1_3__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1672:1: rule__GeoJSON__Group_1_3_1_3__1__Impl : ( ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 ) ) ;
    public final void rule__GeoJSON__Group_1_3_1_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1676:1: ( ( ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1677:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1677:1: ( ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1678:1: ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_3_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1679:1: ( rule__GeoJSON__GeometryAssignment_1_3_1_3_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1679:2: rule__GeoJSON__GeometryAssignment_1_3_1_3_1
            {
            pushFollow(FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_1_3_1_in_rule__GeoJSON__Group_1_3_1_3__1__Impl3498);
            rule__GeoJSON__GeometryAssignment_1_3_1_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_3_1_3__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_4__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1693:1: rule__GeoJSON__Group_1_4__0 : rule__GeoJSON__Group_1_4__0__Impl rule__GeoJSON__Group_1_4__1 ;
    public final void rule__GeoJSON__Group_1_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1697:1: ( rule__GeoJSON__Group_1_4__0__Impl rule__GeoJSON__Group_1_4__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1698:2: rule__GeoJSON__Group_1_4__0__Impl rule__GeoJSON__Group_1_4__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__0__Impl_in_rule__GeoJSON__Group_1_4__03532);
            rule__GeoJSON__Group_1_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__1_in_rule__GeoJSON__Group_1_4__03535);
            rule__GeoJSON__Group_1_4__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_4__0"


    // $ANTLR start "rule__GeoJSON__Group_1_4__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1705:1: rule__GeoJSON__Group_1_4__0__Impl : ( ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 ) ) ;
    public final void rule__GeoJSON__Group_1_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1709:1: ( ( ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1710:1: ( ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1710:1: ( ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1711:1: ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFlatPropertiesAssignment_1_4_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1712:1: ( rule__GeoJSON__FlatPropertiesAssignment_1_4_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1712:2: rule__GeoJSON__FlatPropertiesAssignment_1_4_0
            {
            pushFollow(FOLLOW_rule__GeoJSON__FlatPropertiesAssignment_1_4_0_in_rule__GeoJSON__Group_1_4__0__Impl3562);
            rule__GeoJSON__FlatPropertiesAssignment_1_4_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFlatPropertiesAssignment_1_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_4__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_4__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1722:1: rule__GeoJSON__Group_1_4__1 : rule__GeoJSON__Group_1_4__1__Impl ;
    public final void rule__GeoJSON__Group_1_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1726:1: ( rule__GeoJSON__Group_1_4__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1727:2: rule__GeoJSON__Group_1_4__1__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__1__Impl_in_rule__GeoJSON__Group_1_4__13592);
            rule__GeoJSON__Group_1_4__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_4__1"


    // $ANTLR start "rule__GeoJSON__Group_1_4__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1733:1: rule__GeoJSON__Group_1_4__1__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1737:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1738:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1738:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1739:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_4_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1740:1: ( ',' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==29) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1741:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_4__1__Impl3621); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_4_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_4__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_5__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1756:1: rule__GeoJSON__Group_1_5__0 : rule__GeoJSON__Group_1_5__0__Impl rule__GeoJSON__Group_1_5__1 ;
    public final void rule__GeoJSON__Group_1_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1760:1: ( rule__GeoJSON__Group_1_5__0__Impl rule__GeoJSON__Group_1_5__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1761:2: rule__GeoJSON__Group_1_5__0__Impl rule__GeoJSON__Group_1_5__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__0__Impl_in_rule__GeoJSON__Group_1_5__03658);
            rule__GeoJSON__Group_1_5__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__1_in_rule__GeoJSON__Group_1_5__03661);
            rule__GeoJSON__Group_1_5__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__0"


    // $ANTLR start "rule__GeoJSON__Group_1_5__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1768:1: rule__GeoJSON__Group_1_5__0__Impl : ( '\"properties\"' ) ;
    public final void rule__GeoJSON__Group_1_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1772:1: ( ( '\"properties\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1773:1: ( '\"properties\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1773:1: ( '\"properties\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1774:1: '\"properties\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getPropertiesKeyword_1_5_0()); 
            }
            match(input,34,FOLLOW_34_in_rule__GeoJSON__Group_1_5__0__Impl3689); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getPropertiesKeyword_1_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_5__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1787:1: rule__GeoJSON__Group_1_5__1 : rule__GeoJSON__Group_1_5__1__Impl rule__GeoJSON__Group_1_5__2 ;
    public final void rule__GeoJSON__Group_1_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1791:1: ( rule__GeoJSON__Group_1_5__1__Impl rule__GeoJSON__Group_1_5__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1792:2: rule__GeoJSON__Group_1_5__1__Impl rule__GeoJSON__Group_1_5__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__1__Impl_in_rule__GeoJSON__Group_1_5__13720);
            rule__GeoJSON__Group_1_5__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__2_in_rule__GeoJSON__Group_1_5__13723);
            rule__GeoJSON__Group_1_5__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__1"


    // $ANTLR start "rule__GeoJSON__Group_1_5__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1799:1: rule__GeoJSON__Group_1_5__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1803:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1804:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1804:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1805:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_5_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_5__1__Impl3751); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_5_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_5__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1818:1: rule__GeoJSON__Group_1_5__2 : rule__GeoJSON__Group_1_5__2__Impl rule__GeoJSON__Group_1_5__3 ;
    public final void rule__GeoJSON__Group_1_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1822:1: ( rule__GeoJSON__Group_1_5__2__Impl rule__GeoJSON__Group_1_5__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1823:2: rule__GeoJSON__Group_1_5__2__Impl rule__GeoJSON__Group_1_5__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__2__Impl_in_rule__GeoJSON__Group_1_5__23782);
            rule__GeoJSON__Group_1_5__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__3_in_rule__GeoJSON__Group_1_5__23785);
            rule__GeoJSON__Group_1_5__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__2"


    // $ANTLR start "rule__GeoJSON__Group_1_5__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1830:1: rule__GeoJSON__Group_1_5__2__Impl : ( ( rule__GeoJSON__PropertiesAssignment_1_5_2 ) ) ;
    public final void rule__GeoJSON__Group_1_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1834:1: ( ( ( rule__GeoJSON__PropertiesAssignment_1_5_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1835:1: ( ( rule__GeoJSON__PropertiesAssignment_1_5_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1835:1: ( ( rule__GeoJSON__PropertiesAssignment_1_5_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1836:1: ( rule__GeoJSON__PropertiesAssignment_1_5_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getPropertiesAssignment_1_5_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1837:1: ( rule__GeoJSON__PropertiesAssignment_1_5_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1837:2: rule__GeoJSON__PropertiesAssignment_1_5_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__PropertiesAssignment_1_5_2_in_rule__GeoJSON__Group_1_5__2__Impl3812);
            rule__GeoJSON__PropertiesAssignment_1_5_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getPropertiesAssignment_1_5_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_5__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1847:1: rule__GeoJSON__Group_1_5__3 : rule__GeoJSON__Group_1_5__3__Impl ;
    public final void rule__GeoJSON__Group_1_5__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1851:1: ( rule__GeoJSON__Group_1_5__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1852:2: rule__GeoJSON__Group_1_5__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__3__Impl_in_rule__GeoJSON__Group_1_5__33842);
            rule__GeoJSON__Group_1_5__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__3"


    // $ANTLR start "rule__GeoJSON__Group_1_5__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1858:1: rule__GeoJSON__Group_1_5__3__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_5__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1862:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1863:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1863:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1864:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_5_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1865:1: ( ',' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==29) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1866:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_5__3__Impl3871); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_5_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_5__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_6__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1885:1: rule__GeoJSON__Group_1_6__0 : rule__GeoJSON__Group_1_6__0__Impl rule__GeoJSON__Group_1_6__1 ;
    public final void rule__GeoJSON__Group_1_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1889:1: ( rule__GeoJSON__Group_1_6__0__Impl rule__GeoJSON__Group_1_6__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1890:2: rule__GeoJSON__Group_1_6__0__Impl rule__GeoJSON__Group_1_6__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__0__Impl_in_rule__GeoJSON__Group_1_6__03912);
            rule__GeoJSON__Group_1_6__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__1_in_rule__GeoJSON__Group_1_6__03915);
            rule__GeoJSON__Group_1_6__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__0"


    // $ANTLR start "rule__GeoJSON__Group_1_6__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1897:1: rule__GeoJSON__Group_1_6__0__Impl : ( '\"style\"' ) ;
    public final void rule__GeoJSON__Group_1_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1901:1: ( ( '\"style\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1902:1: ( '\"style\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1902:1: ( '\"style\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1903:1: '\"style\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getStyleKeyword_1_6_0()); 
            }
            match(input,35,FOLLOW_35_in_rule__GeoJSON__Group_1_6__0__Impl3943); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getStyleKeyword_1_6_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_6__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1916:1: rule__GeoJSON__Group_1_6__1 : rule__GeoJSON__Group_1_6__1__Impl rule__GeoJSON__Group_1_6__2 ;
    public final void rule__GeoJSON__Group_1_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1920:1: ( rule__GeoJSON__Group_1_6__1__Impl rule__GeoJSON__Group_1_6__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1921:2: rule__GeoJSON__Group_1_6__1__Impl rule__GeoJSON__Group_1_6__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__1__Impl_in_rule__GeoJSON__Group_1_6__13974);
            rule__GeoJSON__Group_1_6__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__2_in_rule__GeoJSON__Group_1_6__13977);
            rule__GeoJSON__Group_1_6__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__1"


    // $ANTLR start "rule__GeoJSON__Group_1_6__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1928:1: rule__GeoJSON__Group_1_6__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1932:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1933:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1933:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1934:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_6_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_6__1__Impl4005); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_6_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_6__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1947:1: rule__GeoJSON__Group_1_6__2 : rule__GeoJSON__Group_1_6__2__Impl rule__GeoJSON__Group_1_6__3 ;
    public final void rule__GeoJSON__Group_1_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1951:1: ( rule__GeoJSON__Group_1_6__2__Impl rule__GeoJSON__Group_1_6__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1952:2: rule__GeoJSON__Group_1_6__2__Impl rule__GeoJSON__Group_1_6__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__2__Impl_in_rule__GeoJSON__Group_1_6__24036);
            rule__GeoJSON__Group_1_6__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__3_in_rule__GeoJSON__Group_1_6__24039);
            rule__GeoJSON__Group_1_6__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__2"


    // $ANTLR start "rule__GeoJSON__Group_1_6__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1959:1: rule__GeoJSON__Group_1_6__2__Impl : ( ( rule__GeoJSON__StyleAssignment_1_6_2 ) ) ;
    public final void rule__GeoJSON__Group_1_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1963:1: ( ( ( rule__GeoJSON__StyleAssignment_1_6_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1964:1: ( ( rule__GeoJSON__StyleAssignment_1_6_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1964:1: ( ( rule__GeoJSON__StyleAssignment_1_6_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1965:1: ( rule__GeoJSON__StyleAssignment_1_6_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getStyleAssignment_1_6_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1966:1: ( rule__GeoJSON__StyleAssignment_1_6_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1966:2: rule__GeoJSON__StyleAssignment_1_6_2
            {
            pushFollow(FOLLOW_rule__GeoJSON__StyleAssignment_1_6_2_in_rule__GeoJSON__Group_1_6__2__Impl4066);
            rule__GeoJSON__StyleAssignment_1_6_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getStyleAssignment_1_6_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_6__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1976:1: rule__GeoJSON__Group_1_6__3 : rule__GeoJSON__Group_1_6__3__Impl ;
    public final void rule__GeoJSON__Group_1_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1980:1: ( rule__GeoJSON__Group_1_6__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1981:2: rule__GeoJSON__Group_1_6__3__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__3__Impl_in_rule__GeoJSON__Group_1_6__34096);
            rule__GeoJSON__Group_1_6__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__3"


    // $ANTLR start "rule__GeoJSON__Group_1_6__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1987:1: rule__GeoJSON__Group_1_6__3__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1991:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1992:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1992:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1993:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_6_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1994:1: ( ',' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==29) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:1995:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_6__3__Impl4125); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_6_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_6__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2014:1: rule__GeoJSON__Group_1_7__0 : rule__GeoJSON__Group_1_7__0__Impl rule__GeoJSON__Group_1_7__1 ;
    public final void rule__GeoJSON__Group_1_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2018:1: ( rule__GeoJSON__Group_1_7__0__Impl rule__GeoJSON__Group_1_7__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2019:2: rule__GeoJSON__Group_1_7__0__Impl rule__GeoJSON__Group_1_7__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__0__Impl_in_rule__GeoJSON__Group_1_7__04166);
            rule__GeoJSON__Group_1_7__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__1_in_rule__GeoJSON__Group_1_7__04169);
            rule__GeoJSON__Group_1_7__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__0"


    // $ANTLR start "rule__GeoJSON__Group_1_7__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2026:1: rule__GeoJSON__Group_1_7__0__Impl : ( '\"features\"' ) ;
    public final void rule__GeoJSON__Group_1_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2030:1: ( ( '\"features\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2031:1: ( '\"features\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2031:1: ( '\"features\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2032:1: '\"features\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFeaturesKeyword_1_7_0()); 
            }
            match(input,36,FOLLOW_36_in_rule__GeoJSON__Group_1_7__0__Impl4197); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFeaturesKeyword_1_7_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2045:1: rule__GeoJSON__Group_1_7__1 : rule__GeoJSON__Group_1_7__1__Impl rule__GeoJSON__Group_1_7__2 ;
    public final void rule__GeoJSON__Group_1_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2049:1: ( rule__GeoJSON__Group_1_7__1__Impl rule__GeoJSON__Group_1_7__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2050:2: rule__GeoJSON__Group_1_7__1__Impl rule__GeoJSON__Group_1_7__2
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__1__Impl_in_rule__GeoJSON__Group_1_7__14228);
            rule__GeoJSON__Group_1_7__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__2_in_rule__GeoJSON__Group_1_7__14231);
            rule__GeoJSON__Group_1_7__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__1"


    // $ANTLR start "rule__GeoJSON__Group_1_7__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2057:1: rule__GeoJSON__Group_1_7__1__Impl : ( ':' ) ;
    public final void rule__GeoJSON__Group_1_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2061:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2062:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2062:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2063:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getColonKeyword_1_7_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__GeoJSON__Group_1_7__1__Impl4259); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getColonKeyword_1_7_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2076:1: rule__GeoJSON__Group_1_7__2 : rule__GeoJSON__Group_1_7__2__Impl rule__GeoJSON__Group_1_7__3 ;
    public final void rule__GeoJSON__Group_1_7__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2080:1: ( rule__GeoJSON__Group_1_7__2__Impl rule__GeoJSON__Group_1_7__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2081:2: rule__GeoJSON__Group_1_7__2__Impl rule__GeoJSON__Group_1_7__3
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__2__Impl_in_rule__GeoJSON__Group_1_7__24290);
            rule__GeoJSON__Group_1_7__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__3_in_rule__GeoJSON__Group_1_7__24293);
            rule__GeoJSON__Group_1_7__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__2"


    // $ANTLR start "rule__GeoJSON__Group_1_7__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2088:1: rule__GeoJSON__Group_1_7__2__Impl : ( '[' ) ;
    public final void rule__GeoJSON__Group_1_7__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2092:1: ( ( '[' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2093:1: ( '[' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2093:1: ( '[' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2094:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getLeftSquareBracketKeyword_1_7_2()); 
            }
            match(input,37,FOLLOW_37_in_rule__GeoJSON__Group_1_7__2__Impl4321); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getLeftSquareBracketKeyword_1_7_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__2__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2107:1: rule__GeoJSON__Group_1_7__3 : rule__GeoJSON__Group_1_7__3__Impl rule__GeoJSON__Group_1_7__4 ;
    public final void rule__GeoJSON__Group_1_7__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2111:1: ( rule__GeoJSON__Group_1_7__3__Impl rule__GeoJSON__Group_1_7__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2112:2: rule__GeoJSON__Group_1_7__3__Impl rule__GeoJSON__Group_1_7__4
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__3__Impl_in_rule__GeoJSON__Group_1_7__34352);
            rule__GeoJSON__Group_1_7__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__4_in_rule__GeoJSON__Group_1_7__34355);
            rule__GeoJSON__Group_1_7__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__3"


    // $ANTLR start "rule__GeoJSON__Group_1_7__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2119:1: rule__GeoJSON__Group_1_7__3__Impl : ( ( rule__GeoJSON__Group_1_7_3__0 )? ) ;
    public final void rule__GeoJSON__Group_1_7__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2123:1: ( ( ( rule__GeoJSON__Group_1_7_3__0 )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2124:1: ( ( rule__GeoJSON__Group_1_7_3__0 )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2124:1: ( ( rule__GeoJSON__Group_1_7_3__0 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2125:1: ( rule__GeoJSON__Group_1_7_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGroup_1_7_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2126:1: ( rule__GeoJSON__Group_1_7_3__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==25) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2126:2: rule__GeoJSON__Group_1_7_3__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3__0_in_rule__GeoJSON__Group_1_7__3__Impl4382);
                    rule__GeoJSON__Group_1_7_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGroup_1_7_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__3__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2136:1: rule__GeoJSON__Group_1_7__4 : rule__GeoJSON__Group_1_7__4__Impl rule__GeoJSON__Group_1_7__5 ;
    public final void rule__GeoJSON__Group_1_7__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2140:1: ( rule__GeoJSON__Group_1_7__4__Impl rule__GeoJSON__Group_1_7__5 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2141:2: rule__GeoJSON__Group_1_7__4__Impl rule__GeoJSON__Group_1_7__5
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__4__Impl_in_rule__GeoJSON__Group_1_7__44413);
            rule__GeoJSON__Group_1_7__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__5_in_rule__GeoJSON__Group_1_7__44416);
            rule__GeoJSON__Group_1_7__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__4"


    // $ANTLR start "rule__GeoJSON__Group_1_7__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2148:1: rule__GeoJSON__Group_1_7__4__Impl : ( ']' ) ;
    public final void rule__GeoJSON__Group_1_7__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2152:1: ( ( ']' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2153:1: ( ']' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2153:1: ( ']' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2154:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getRightSquareBracketKeyword_1_7_4()); 
            }
            match(input,38,FOLLOW_38_in_rule__GeoJSON__Group_1_7__4__Impl4444); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getRightSquareBracketKeyword_1_7_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__4__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2167:1: rule__GeoJSON__Group_1_7__5 : rule__GeoJSON__Group_1_7__5__Impl ;
    public final void rule__GeoJSON__Group_1_7__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2171:1: ( rule__GeoJSON__Group_1_7__5__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2172:2: rule__GeoJSON__Group_1_7__5__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__5__Impl_in_rule__GeoJSON__Group_1_7__54475);
            rule__GeoJSON__Group_1_7__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__5"


    // $ANTLR start "rule__GeoJSON__Group_1_7__5__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2178:1: rule__GeoJSON__Group_1_7__5__Impl : ( ( ',' )? ) ;
    public final void rule__GeoJSON__Group_1_7__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2182:1: ( ( ( ',' )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2183:1: ( ( ',' )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2183:1: ( ( ',' )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2184:1: ( ',' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_7_5()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2185:1: ( ',' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==29) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2186:2: ','
                    {
                    match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_7__5__Impl4504); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_7_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7__5__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2209:1: rule__GeoJSON__Group_1_7_3__0 : rule__GeoJSON__Group_1_7_3__0__Impl rule__GeoJSON__Group_1_7_3__1 ;
    public final void rule__GeoJSON__Group_1_7_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2213:1: ( rule__GeoJSON__Group_1_7_3__0__Impl rule__GeoJSON__Group_1_7_3__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2214:2: rule__GeoJSON__Group_1_7_3__0__Impl rule__GeoJSON__Group_1_7_3__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3__0__Impl_in_rule__GeoJSON__Group_1_7_3__04549);
            rule__GeoJSON__Group_1_7_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3__1_in_rule__GeoJSON__Group_1_7_3__04552);
            rule__GeoJSON__Group_1_7_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3__0"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2221:1: rule__GeoJSON__Group_1_7_3__0__Impl : ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 ) ) ;
    public final void rule__GeoJSON__Group_1_7_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2225:1: ( ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2226:1: ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2226:1: ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2227:1: ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2228:1: ( rule__GeoJSON__FeaturesAssignment_1_7_3_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2228:2: rule__GeoJSON__FeaturesAssignment_1_7_3_0
            {
            pushFollow(FOLLOW_rule__GeoJSON__FeaturesAssignment_1_7_3_0_in_rule__GeoJSON__Group_1_7_3__0__Impl4579);
            rule__GeoJSON__FeaturesAssignment_1_7_3_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2238:1: rule__GeoJSON__Group_1_7_3__1 : rule__GeoJSON__Group_1_7_3__1__Impl ;
    public final void rule__GeoJSON__Group_1_7_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2242:1: ( rule__GeoJSON__Group_1_7_3__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2243:2: rule__GeoJSON__Group_1_7_3__1__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3__1__Impl_in_rule__GeoJSON__Group_1_7_3__14609);
            rule__GeoJSON__Group_1_7_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3__1"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2249:1: rule__GeoJSON__Group_1_7_3__1__Impl : ( ( rule__GeoJSON__Group_1_7_3_1__0 )* ) ;
    public final void rule__GeoJSON__Group_1_7_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2253:1: ( ( ( rule__GeoJSON__Group_1_7_3_1__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2254:1: ( ( rule__GeoJSON__Group_1_7_3_1__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2254:1: ( ( rule__GeoJSON__Group_1_7_3_1__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2255:1: ( rule__GeoJSON__Group_1_7_3_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGroup_1_7_3_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2256:1: ( rule__GeoJSON__Group_1_7_3_1__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==29) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2256:2: rule__GeoJSON__Group_1_7_3_1__0
            	    {
            	    pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3_1__0_in_rule__GeoJSON__Group_1_7_3__1__Impl4636);
            	    rule__GeoJSON__Group_1_7_3_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGroup_1_7_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3__1__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2270:1: rule__GeoJSON__Group_1_7_3_1__0 : rule__GeoJSON__Group_1_7_3_1__0__Impl rule__GeoJSON__Group_1_7_3_1__1 ;
    public final void rule__GeoJSON__Group_1_7_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2274:1: ( rule__GeoJSON__Group_1_7_3_1__0__Impl rule__GeoJSON__Group_1_7_3_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2275:2: rule__GeoJSON__Group_1_7_3_1__0__Impl rule__GeoJSON__Group_1_7_3_1__1
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3_1__0__Impl_in_rule__GeoJSON__Group_1_7_3_1__04671);
            rule__GeoJSON__Group_1_7_3_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3_1__1_in_rule__GeoJSON__Group_1_7_3_1__04674);
            rule__GeoJSON__Group_1_7_3_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3_1__0"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2282:1: rule__GeoJSON__Group_1_7_3_1__0__Impl : ( ',' ) ;
    public final void rule__GeoJSON__Group_1_7_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2286:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2287:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2287:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2288:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_7_3_1_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__GeoJSON__Group_1_7_3_1__0__Impl4702); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCommaKeyword_1_7_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3_1__0__Impl"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2301:1: rule__GeoJSON__Group_1_7_3_1__1 : rule__GeoJSON__Group_1_7_3_1__1__Impl ;
    public final void rule__GeoJSON__Group_1_7_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2305:1: ( rule__GeoJSON__Group_1_7_3_1__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2306:2: rule__GeoJSON__Group_1_7_3_1__1__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__Group_1_7_3_1__1__Impl_in_rule__GeoJSON__Group_1_7_3_1__14733);
            rule__GeoJSON__Group_1_7_3_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3_1__1"


    // $ANTLR start "rule__GeoJSON__Group_1_7_3_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2312:1: rule__GeoJSON__Group_1_7_3_1__1__Impl : ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 ) ) ;
    public final void rule__GeoJSON__Group_1_7_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2316:1: ( ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2317:1: ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2317:1: ( ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2318:1: ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_1_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2319:1: ( rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2319:2: rule__GeoJSON__FeaturesAssignment_1_7_3_1_1
            {
            pushFollow(FOLLOW_rule__GeoJSON__FeaturesAssignment_1_7_3_1_1_in_rule__GeoJSON__Group_1_7_3_1__1__Impl4760);
            rule__GeoJSON__FeaturesAssignment_1_7_3_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__Group_1_7_3_1__1__Impl"


    // $ANTLR start "rule__CRS__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2333:1: rule__CRS__Group__0 : rule__CRS__Group__0__Impl rule__CRS__Group__1 ;
    public final void rule__CRS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2337:1: ( rule__CRS__Group__0__Impl rule__CRS__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2338:2: rule__CRS__Group__0__Impl rule__CRS__Group__1
            {
            pushFollow(FOLLOW_rule__CRS__Group__0__Impl_in_rule__CRS__Group__04794);
            rule__CRS__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__1_in_rule__CRS__Group__04797);
            rule__CRS__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__0"


    // $ANTLR start "rule__CRS__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2345:1: rule__CRS__Group__0__Impl : ( '{' ) ;
    public final void rule__CRS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2349:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2350:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2350:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2351:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getLeftCurlyBracketKeyword_0()); 
            }
            match(input,25,FOLLOW_25_in_rule__CRS__Group__0__Impl4825); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getLeftCurlyBracketKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__0__Impl"


    // $ANTLR start "rule__CRS__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2364:1: rule__CRS__Group__1 : rule__CRS__Group__1__Impl rule__CRS__Group__2 ;
    public final void rule__CRS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2368:1: ( rule__CRS__Group__1__Impl rule__CRS__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2369:2: rule__CRS__Group__1__Impl rule__CRS__Group__2
            {
            pushFollow(FOLLOW_rule__CRS__Group__1__Impl_in_rule__CRS__Group__14856);
            rule__CRS__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__2_in_rule__CRS__Group__14859);
            rule__CRS__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__1"


    // $ANTLR start "rule__CRS__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2376:1: rule__CRS__Group__1__Impl : ( '\"type\"' ) ;
    public final void rule__CRS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2380:1: ( ( '\"type\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2381:1: ( '\"type\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2381:1: ( '\"type\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2382:1: '\"type\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getTypeKeyword_1()); 
            }
            match(input,27,FOLLOW_27_in_rule__CRS__Group__1__Impl4887); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getTypeKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__1__Impl"


    // $ANTLR start "rule__CRS__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2395:1: rule__CRS__Group__2 : rule__CRS__Group__2__Impl rule__CRS__Group__3 ;
    public final void rule__CRS__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2399:1: ( rule__CRS__Group__2__Impl rule__CRS__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2400:2: rule__CRS__Group__2__Impl rule__CRS__Group__3
            {
            pushFollow(FOLLOW_rule__CRS__Group__2__Impl_in_rule__CRS__Group__24918);
            rule__CRS__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__3_in_rule__CRS__Group__24921);
            rule__CRS__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__2"


    // $ANTLR start "rule__CRS__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2407:1: rule__CRS__Group__2__Impl : ( ':' ) ;
    public final void rule__CRS__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2411:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2412:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2412:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2413:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getColonKeyword_2()); 
            }
            match(input,28,FOLLOW_28_in_rule__CRS__Group__2__Impl4949); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getColonKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__2__Impl"


    // $ANTLR start "rule__CRS__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2426:1: rule__CRS__Group__3 : rule__CRS__Group__3__Impl rule__CRS__Group__4 ;
    public final void rule__CRS__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2430:1: ( rule__CRS__Group__3__Impl rule__CRS__Group__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2431:2: rule__CRS__Group__3__Impl rule__CRS__Group__4
            {
            pushFollow(FOLLOW_rule__CRS__Group__3__Impl_in_rule__CRS__Group__34980);
            rule__CRS__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__4_in_rule__CRS__Group__34983);
            rule__CRS__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__3"


    // $ANTLR start "rule__CRS__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2438:1: rule__CRS__Group__3__Impl : ( ( rule__CRS__TypeAssignment_3 ) ) ;
    public final void rule__CRS__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2442:1: ( ( ( rule__CRS__TypeAssignment_3 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2443:1: ( ( rule__CRS__TypeAssignment_3 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2443:1: ( ( rule__CRS__TypeAssignment_3 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2444:1: ( rule__CRS__TypeAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getTypeAssignment_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2445:1: ( rule__CRS__TypeAssignment_3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2445:2: rule__CRS__TypeAssignment_3
            {
            pushFollow(FOLLOW_rule__CRS__TypeAssignment_3_in_rule__CRS__Group__3__Impl5010);
            rule__CRS__TypeAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getTypeAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__3__Impl"


    // $ANTLR start "rule__CRS__Group__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2455:1: rule__CRS__Group__4 : rule__CRS__Group__4__Impl rule__CRS__Group__5 ;
    public final void rule__CRS__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2459:1: ( rule__CRS__Group__4__Impl rule__CRS__Group__5 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2460:2: rule__CRS__Group__4__Impl rule__CRS__Group__5
            {
            pushFollow(FOLLOW_rule__CRS__Group__4__Impl_in_rule__CRS__Group__45040);
            rule__CRS__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__5_in_rule__CRS__Group__45043);
            rule__CRS__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__4"


    // $ANTLR start "rule__CRS__Group__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2467:1: rule__CRS__Group__4__Impl : ( ',' ) ;
    public final void rule__CRS__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2471:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2472:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2472:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2473:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getCommaKeyword_4()); 
            }
            match(input,29,FOLLOW_29_in_rule__CRS__Group__4__Impl5071); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getCommaKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__4__Impl"


    // $ANTLR start "rule__CRS__Group__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2486:1: rule__CRS__Group__5 : rule__CRS__Group__5__Impl rule__CRS__Group__6 ;
    public final void rule__CRS__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2490:1: ( rule__CRS__Group__5__Impl rule__CRS__Group__6 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2491:2: rule__CRS__Group__5__Impl rule__CRS__Group__6
            {
            pushFollow(FOLLOW_rule__CRS__Group__5__Impl_in_rule__CRS__Group__55102);
            rule__CRS__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__6_in_rule__CRS__Group__55105);
            rule__CRS__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__5"


    // $ANTLR start "rule__CRS__Group__5__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2498:1: rule__CRS__Group__5__Impl : ( '\"properties\"' ) ;
    public final void rule__CRS__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2502:1: ( ( '\"properties\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2503:1: ( '\"properties\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2503:1: ( '\"properties\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2504:1: '\"properties\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getPropertiesKeyword_5()); 
            }
            match(input,34,FOLLOW_34_in_rule__CRS__Group__5__Impl5133); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getPropertiesKeyword_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__5__Impl"


    // $ANTLR start "rule__CRS__Group__6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2517:1: rule__CRS__Group__6 : rule__CRS__Group__6__Impl rule__CRS__Group__7 ;
    public final void rule__CRS__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2521:1: ( rule__CRS__Group__6__Impl rule__CRS__Group__7 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2522:2: rule__CRS__Group__6__Impl rule__CRS__Group__7
            {
            pushFollow(FOLLOW_rule__CRS__Group__6__Impl_in_rule__CRS__Group__65164);
            rule__CRS__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__7_in_rule__CRS__Group__65167);
            rule__CRS__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__6"


    // $ANTLR start "rule__CRS__Group__6__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2529:1: rule__CRS__Group__6__Impl : ( ':' ) ;
    public final void rule__CRS__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2533:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2534:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2534:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2535:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getColonKeyword_6()); 
            }
            match(input,28,FOLLOW_28_in_rule__CRS__Group__6__Impl5195); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getColonKeyword_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__6__Impl"


    // $ANTLR start "rule__CRS__Group__7"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2548:1: rule__CRS__Group__7 : rule__CRS__Group__7__Impl rule__CRS__Group__8 ;
    public final void rule__CRS__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2552:1: ( rule__CRS__Group__7__Impl rule__CRS__Group__8 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2553:2: rule__CRS__Group__7__Impl rule__CRS__Group__8
            {
            pushFollow(FOLLOW_rule__CRS__Group__7__Impl_in_rule__CRS__Group__75226);
            rule__CRS__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__8_in_rule__CRS__Group__75229);
            rule__CRS__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__7"


    // $ANTLR start "rule__CRS__Group__7__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2560:1: rule__CRS__Group__7__Impl : ( '{' ) ;
    public final void rule__CRS__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2564:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2565:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2565:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2566:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getLeftCurlyBracketKeyword_7()); 
            }
            match(input,25,FOLLOW_25_in_rule__CRS__Group__7__Impl5257); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getLeftCurlyBracketKeyword_7()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__7__Impl"


    // $ANTLR start "rule__CRS__Group__8"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2579:1: rule__CRS__Group__8 : rule__CRS__Group__8__Impl rule__CRS__Group__9 ;
    public final void rule__CRS__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2583:1: ( rule__CRS__Group__8__Impl rule__CRS__Group__9 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2584:2: rule__CRS__Group__8__Impl rule__CRS__Group__9
            {
            pushFollow(FOLLOW_rule__CRS__Group__8__Impl_in_rule__CRS__Group__85288);
            rule__CRS__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__9_in_rule__CRS__Group__85291);
            rule__CRS__Group__9();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__8"


    // $ANTLR start "rule__CRS__Group__8__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2591:1: rule__CRS__Group__8__Impl : ( ( rule__CRS__Alternatives_8 ) ) ;
    public final void rule__CRS__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2595:1: ( ( ( rule__CRS__Alternatives_8 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2596:1: ( ( rule__CRS__Alternatives_8 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2596:1: ( ( rule__CRS__Alternatives_8 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2597:1: ( rule__CRS__Alternatives_8 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getAlternatives_8()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2598:1: ( rule__CRS__Alternatives_8 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2598:2: rule__CRS__Alternatives_8
            {
            pushFollow(FOLLOW_rule__CRS__Alternatives_8_in_rule__CRS__Group__8__Impl5318);
            rule__CRS__Alternatives_8();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getAlternatives_8()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__8__Impl"


    // $ANTLR start "rule__CRS__Group__9"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2608:1: rule__CRS__Group__9 : rule__CRS__Group__9__Impl rule__CRS__Group__10 ;
    public final void rule__CRS__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2612:1: ( rule__CRS__Group__9__Impl rule__CRS__Group__10 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2613:2: rule__CRS__Group__9__Impl rule__CRS__Group__10
            {
            pushFollow(FOLLOW_rule__CRS__Group__9__Impl_in_rule__CRS__Group__95348);
            rule__CRS__Group__9__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group__10_in_rule__CRS__Group__95351);
            rule__CRS__Group__10();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__9"


    // $ANTLR start "rule__CRS__Group__9__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2620:1: rule__CRS__Group__9__Impl : ( '}' ) ;
    public final void rule__CRS__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2624:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2625:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2625:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2626:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getRightCurlyBracketKeyword_9()); 
            }
            match(input,26,FOLLOW_26_in_rule__CRS__Group__9__Impl5379); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getRightCurlyBracketKeyword_9()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__9__Impl"


    // $ANTLR start "rule__CRS__Group__10"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2639:1: rule__CRS__Group__10 : rule__CRS__Group__10__Impl ;
    public final void rule__CRS__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2643:1: ( rule__CRS__Group__10__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2644:2: rule__CRS__Group__10__Impl
            {
            pushFollow(FOLLOW_rule__CRS__Group__10__Impl_in_rule__CRS__Group__105410);
            rule__CRS__Group__10__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__10"


    // $ANTLR start "rule__CRS__Group__10__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2650:1: rule__CRS__Group__10__Impl : ( '}' ) ;
    public final void rule__CRS__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2654:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2655:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2655:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2656:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getRightCurlyBracketKeyword_10()); 
            }
            match(input,26,FOLLOW_26_in_rule__CRS__Group__10__Impl5438); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getRightCurlyBracketKeyword_10()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group__10__Impl"


    // $ANTLR start "rule__CRS__Group_8_0__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2691:1: rule__CRS__Group_8_0__0 : rule__CRS__Group_8_0__0__Impl rule__CRS__Group_8_0__1 ;
    public final void rule__CRS__Group_8_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2695:1: ( rule__CRS__Group_8_0__0__Impl rule__CRS__Group_8_0__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2696:2: rule__CRS__Group_8_0__0__Impl rule__CRS__Group_8_0__1
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_0__0__Impl_in_rule__CRS__Group_8_0__05491);
            rule__CRS__Group_8_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_0__1_in_rule__CRS__Group_8_0__05494);
            rule__CRS__Group_8_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__0"


    // $ANTLR start "rule__CRS__Group_8_0__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2703:1: rule__CRS__Group_8_0__0__Impl : ( '\"name\"' ) ;
    public final void rule__CRS__Group_8_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2707:1: ( ( '\"name\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2708:1: ( '\"name\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2708:1: ( '\"name\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2709:1: '\"name\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getNameKeyword_8_0_0()); 
            }
            match(input,20,FOLLOW_20_in_rule__CRS__Group_8_0__0__Impl5522); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getNameKeyword_8_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__0__Impl"


    // $ANTLR start "rule__CRS__Group_8_0__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2722:1: rule__CRS__Group_8_0__1 : rule__CRS__Group_8_0__1__Impl rule__CRS__Group_8_0__2 ;
    public final void rule__CRS__Group_8_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2726:1: ( rule__CRS__Group_8_0__1__Impl rule__CRS__Group_8_0__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2727:2: rule__CRS__Group_8_0__1__Impl rule__CRS__Group_8_0__2
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_0__1__Impl_in_rule__CRS__Group_8_0__15553);
            rule__CRS__Group_8_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_0__2_in_rule__CRS__Group_8_0__15556);
            rule__CRS__Group_8_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__1"


    // $ANTLR start "rule__CRS__Group_8_0__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2734:1: rule__CRS__Group_8_0__1__Impl : ( ':' ) ;
    public final void rule__CRS__Group_8_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2738:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2739:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2739:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2740:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getColonKeyword_8_0_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__CRS__Group_8_0__1__Impl5584); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getColonKeyword_8_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__1__Impl"


    // $ANTLR start "rule__CRS__Group_8_0__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2753:1: rule__CRS__Group_8_0__2 : rule__CRS__Group_8_0__2__Impl ;
    public final void rule__CRS__Group_8_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2757:1: ( rule__CRS__Group_8_0__2__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2758:2: rule__CRS__Group_8_0__2__Impl
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_0__2__Impl_in_rule__CRS__Group_8_0__25615);
            rule__CRS__Group_8_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__2"


    // $ANTLR start "rule__CRS__Group_8_0__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2764:1: rule__CRS__Group_8_0__2__Impl : ( ( rule__CRS__NameAssignment_8_0_2 ) ) ;
    public final void rule__CRS__Group_8_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2768:1: ( ( ( rule__CRS__NameAssignment_8_0_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2769:1: ( ( rule__CRS__NameAssignment_8_0_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2769:1: ( ( rule__CRS__NameAssignment_8_0_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2770:1: ( rule__CRS__NameAssignment_8_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getNameAssignment_8_0_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2771:1: ( rule__CRS__NameAssignment_8_0_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2771:2: rule__CRS__NameAssignment_8_0_2
            {
            pushFollow(FOLLOW_rule__CRS__NameAssignment_8_0_2_in_rule__CRS__Group_8_0__2__Impl5642);
            rule__CRS__NameAssignment_8_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getNameAssignment_8_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_0__2__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2787:1: rule__CRS__Group_8_1__0 : rule__CRS__Group_8_1__0__Impl rule__CRS__Group_8_1__1 ;
    public final void rule__CRS__Group_8_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2791:1: ( rule__CRS__Group_8_1__0__Impl rule__CRS__Group_8_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2792:2: rule__CRS__Group_8_1__0__Impl rule__CRS__Group_8_1__1
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__0__Impl_in_rule__CRS__Group_8_1__05678);
            rule__CRS__Group_8_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__1_in_rule__CRS__Group_8_1__05681);
            rule__CRS__Group_8_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__0"


    // $ANTLR start "rule__CRS__Group_8_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2799:1: rule__CRS__Group_8_1__0__Impl : ( '\"href\"' ) ;
    public final void rule__CRS__Group_8_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2803:1: ( ( '\"href\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2804:1: ( '\"href\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2804:1: ( '\"href\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2805:1: '\"href\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getHrefKeyword_8_1_0()); 
            }
            match(input,39,FOLLOW_39_in_rule__CRS__Group_8_1__0__Impl5709); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getHrefKeyword_8_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__0__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2818:1: rule__CRS__Group_8_1__1 : rule__CRS__Group_8_1__1__Impl rule__CRS__Group_8_1__2 ;
    public final void rule__CRS__Group_8_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2822:1: ( rule__CRS__Group_8_1__1__Impl rule__CRS__Group_8_1__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2823:2: rule__CRS__Group_8_1__1__Impl rule__CRS__Group_8_1__2
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__1__Impl_in_rule__CRS__Group_8_1__15740);
            rule__CRS__Group_8_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__2_in_rule__CRS__Group_8_1__15743);
            rule__CRS__Group_8_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__1"


    // $ANTLR start "rule__CRS__Group_8_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2830:1: rule__CRS__Group_8_1__1__Impl : ( ':' ) ;
    public final void rule__CRS__Group_8_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2834:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2835:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2835:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2836:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getColonKeyword_8_1_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__CRS__Group_8_1__1__Impl5771); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getColonKeyword_8_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__1__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2849:1: rule__CRS__Group_8_1__2 : rule__CRS__Group_8_1__2__Impl rule__CRS__Group_8_1__3 ;
    public final void rule__CRS__Group_8_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2853:1: ( rule__CRS__Group_8_1__2__Impl rule__CRS__Group_8_1__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2854:2: rule__CRS__Group_8_1__2__Impl rule__CRS__Group_8_1__3
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__2__Impl_in_rule__CRS__Group_8_1__25802);
            rule__CRS__Group_8_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__3_in_rule__CRS__Group_8_1__25805);
            rule__CRS__Group_8_1__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__2"


    // $ANTLR start "rule__CRS__Group_8_1__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2861:1: rule__CRS__Group_8_1__2__Impl : ( RULE_STRING ) ;
    public final void rule__CRS__Group_8_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2865:1: ( ( RULE_STRING ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2866:1: ( RULE_STRING )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2866:1: ( RULE_STRING )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2867:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getSTRINGTerminalRuleCall_8_1_2()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__CRS__Group_8_1__2__Impl5832); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getSTRINGTerminalRuleCall_8_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__2__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2878:1: rule__CRS__Group_8_1__3 : rule__CRS__Group_8_1__3__Impl rule__CRS__Group_8_1__4 ;
    public final void rule__CRS__Group_8_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2882:1: ( rule__CRS__Group_8_1__3__Impl rule__CRS__Group_8_1__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2883:2: rule__CRS__Group_8_1__3__Impl rule__CRS__Group_8_1__4
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__3__Impl_in_rule__CRS__Group_8_1__35861);
            rule__CRS__Group_8_1__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__4_in_rule__CRS__Group_8_1__35864);
            rule__CRS__Group_8_1__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__3"


    // $ANTLR start "rule__CRS__Group_8_1__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2890:1: rule__CRS__Group_8_1__3__Impl : ( ',' ) ;
    public final void rule__CRS__Group_8_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2894:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2895:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2895:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2896:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getCommaKeyword_8_1_3()); 
            }
            match(input,29,FOLLOW_29_in_rule__CRS__Group_8_1__3__Impl5892); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getCommaKeyword_8_1_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__3__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2909:1: rule__CRS__Group_8_1__4 : rule__CRS__Group_8_1__4__Impl rule__CRS__Group_8_1__5 ;
    public final void rule__CRS__Group_8_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2913:1: ( rule__CRS__Group_8_1__4__Impl rule__CRS__Group_8_1__5 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2914:2: rule__CRS__Group_8_1__4__Impl rule__CRS__Group_8_1__5
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__4__Impl_in_rule__CRS__Group_8_1__45923);
            rule__CRS__Group_8_1__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__5_in_rule__CRS__Group_8_1__45926);
            rule__CRS__Group_8_1__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__4"


    // $ANTLR start "rule__CRS__Group_8_1__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2921:1: rule__CRS__Group_8_1__4__Impl : ( '\"type\"' ) ;
    public final void rule__CRS__Group_8_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2925:1: ( ( '\"type\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2926:1: ( '\"type\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2926:1: ( '\"type\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2927:1: '\"type\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getTypeKeyword_8_1_4()); 
            }
            match(input,27,FOLLOW_27_in_rule__CRS__Group_8_1__4__Impl5954); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getTypeKeyword_8_1_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__4__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2940:1: rule__CRS__Group_8_1__5 : rule__CRS__Group_8_1__5__Impl rule__CRS__Group_8_1__6 ;
    public final void rule__CRS__Group_8_1__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2944:1: ( rule__CRS__Group_8_1__5__Impl rule__CRS__Group_8_1__6 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2945:2: rule__CRS__Group_8_1__5__Impl rule__CRS__Group_8_1__6
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__5__Impl_in_rule__CRS__Group_8_1__55985);
            rule__CRS__Group_8_1__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CRS__Group_8_1__6_in_rule__CRS__Group_8_1__55988);
            rule__CRS__Group_8_1__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__5"


    // $ANTLR start "rule__CRS__Group_8_1__5__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2952:1: rule__CRS__Group_8_1__5__Impl : ( ':' ) ;
    public final void rule__CRS__Group_8_1__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2956:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2957:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2957:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2958:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getColonKeyword_8_1_5()); 
            }
            match(input,28,FOLLOW_28_in_rule__CRS__Group_8_1__5__Impl6016); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getColonKeyword_8_1_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__5__Impl"


    // $ANTLR start "rule__CRS__Group_8_1__6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2971:1: rule__CRS__Group_8_1__6 : rule__CRS__Group_8_1__6__Impl ;
    public final void rule__CRS__Group_8_1__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2975:1: ( rule__CRS__Group_8_1__6__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2976:2: rule__CRS__Group_8_1__6__Impl
            {
            pushFollow(FOLLOW_rule__CRS__Group_8_1__6__Impl_in_rule__CRS__Group_8_1__66047);
            rule__CRS__Group_8_1__6__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__6"


    // $ANTLR start "rule__CRS__Group_8_1__6__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2982:1: rule__CRS__Group_8_1__6__Impl : ( ( rule__CRS__LinkCRSTypeAssignment_8_1_6 ) ) ;
    public final void rule__CRS__Group_8_1__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2986:1: ( ( ( rule__CRS__LinkCRSTypeAssignment_8_1_6 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2987:1: ( ( rule__CRS__LinkCRSTypeAssignment_8_1_6 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2987:1: ( ( rule__CRS__LinkCRSTypeAssignment_8_1_6 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2988:1: ( rule__CRS__LinkCRSTypeAssignment_8_1_6 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getLinkCRSTypeAssignment_8_1_6()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2989:1: ( rule__CRS__LinkCRSTypeAssignment_8_1_6 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:2989:2: rule__CRS__LinkCRSTypeAssignment_8_1_6
            {
            pushFollow(FOLLOW_rule__CRS__LinkCRSTypeAssignment_8_1_6_in_rule__CRS__Group_8_1__6__Impl6074);
            rule__CRS__LinkCRSTypeAssignment_8_1_6();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getLinkCRSTypeAssignment_8_1_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__Group_8_1__6__Impl"


    // $ANTLR start "rule__CoordList__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3013:1: rule__CoordList__Group__0 : rule__CoordList__Group__0__Impl rule__CoordList__Group__1 ;
    public final void rule__CoordList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3017:1: ( rule__CoordList__Group__0__Impl rule__CoordList__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3018:2: rule__CoordList__Group__0__Impl rule__CoordList__Group__1
            {
            pushFollow(FOLLOW_rule__CoordList__Group__0__Impl_in_rule__CoordList__Group__06118);
            rule__CoordList__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordList__Group__1_in_rule__CoordList__Group__06121);
            rule__CoordList__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__0"


    // $ANTLR start "rule__CoordList__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3025:1: rule__CoordList__Group__0__Impl : ( '[' ) ;
    public final void rule__CoordList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3029:1: ( ( '[' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3030:1: ( '[' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3030:1: ( '[' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3031:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getLeftSquareBracketKeyword_0()); 
            }
            match(input,37,FOLLOW_37_in_rule__CoordList__Group__0__Impl6149); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getLeftSquareBracketKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__0__Impl"


    // $ANTLR start "rule__CoordList__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3044:1: rule__CoordList__Group__1 : rule__CoordList__Group__1__Impl rule__CoordList__Group__2 ;
    public final void rule__CoordList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3048:1: ( rule__CoordList__Group__1__Impl rule__CoordList__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3049:2: rule__CoordList__Group__1__Impl rule__CoordList__Group__2
            {
            pushFollow(FOLLOW_rule__CoordList__Group__1__Impl_in_rule__CoordList__Group__16180);
            rule__CoordList__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordList__Group__2_in_rule__CoordList__Group__16183);
            rule__CoordList__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__1"


    // $ANTLR start "rule__CoordList__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3056:1: rule__CoordList__Group__1__Impl : ( ( rule__CoordList__CoordsAssignment_1 ) ) ;
    public final void rule__CoordList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3060:1: ( ( ( rule__CoordList__CoordsAssignment_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3061:1: ( ( rule__CoordList__CoordsAssignment_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3061:1: ( ( rule__CoordList__CoordsAssignment_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3062:1: ( rule__CoordList__CoordsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getCoordsAssignment_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3063:1: ( rule__CoordList__CoordsAssignment_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3063:2: rule__CoordList__CoordsAssignment_1
            {
            pushFollow(FOLLOW_rule__CoordList__CoordsAssignment_1_in_rule__CoordList__Group__1__Impl6210);
            rule__CoordList__CoordsAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getCoordsAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__1__Impl"


    // $ANTLR start "rule__CoordList__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3073:1: rule__CoordList__Group__2 : rule__CoordList__Group__2__Impl rule__CoordList__Group__3 ;
    public final void rule__CoordList__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3077:1: ( rule__CoordList__Group__2__Impl rule__CoordList__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3078:2: rule__CoordList__Group__2__Impl rule__CoordList__Group__3
            {
            pushFollow(FOLLOW_rule__CoordList__Group__2__Impl_in_rule__CoordList__Group__26240);
            rule__CoordList__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordList__Group__3_in_rule__CoordList__Group__26243);
            rule__CoordList__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__2"


    // $ANTLR start "rule__CoordList__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3085:1: rule__CoordList__Group__2__Impl : ( ( rule__CoordList__Group_2__0 )* ) ;
    public final void rule__CoordList__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3089:1: ( ( ( rule__CoordList__Group_2__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3090:1: ( ( rule__CoordList__Group_2__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3090:1: ( ( rule__CoordList__Group_2__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3091:1: ( rule__CoordList__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getGroup_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3092:1: ( rule__CoordList__Group_2__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==29) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3092:2: rule__CoordList__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__CoordList__Group_2__0_in_rule__CoordList__Group__2__Impl6270);
            	    rule__CoordList__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__2__Impl"


    // $ANTLR start "rule__CoordList__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3102:1: rule__CoordList__Group__3 : rule__CoordList__Group__3__Impl ;
    public final void rule__CoordList__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3106:1: ( rule__CoordList__Group__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3107:2: rule__CoordList__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__CoordList__Group__3__Impl_in_rule__CoordList__Group__36301);
            rule__CoordList__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__3"


    // $ANTLR start "rule__CoordList__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3113:1: rule__CoordList__Group__3__Impl : ( ']' ) ;
    public final void rule__CoordList__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3117:1: ( ( ']' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3118:1: ( ']' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3118:1: ( ']' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3119:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getRightSquareBracketKeyword_3()); 
            }
            match(input,38,FOLLOW_38_in_rule__CoordList__Group__3__Impl6329); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getRightSquareBracketKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group__3__Impl"


    // $ANTLR start "rule__CoordList__Group_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3140:1: rule__CoordList__Group_2__0 : rule__CoordList__Group_2__0__Impl rule__CoordList__Group_2__1 ;
    public final void rule__CoordList__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3144:1: ( rule__CoordList__Group_2__0__Impl rule__CoordList__Group_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3145:2: rule__CoordList__Group_2__0__Impl rule__CoordList__Group_2__1
            {
            pushFollow(FOLLOW_rule__CoordList__Group_2__0__Impl_in_rule__CoordList__Group_2__06368);
            rule__CoordList__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordList__Group_2__1_in_rule__CoordList__Group_2__06371);
            rule__CoordList__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group_2__0"


    // $ANTLR start "rule__CoordList__Group_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3152:1: rule__CoordList__Group_2__0__Impl : ( ',' ) ;
    public final void rule__CoordList__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3156:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3157:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3157:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3158:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getCommaKeyword_2_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__CoordList__Group_2__0__Impl6399); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getCommaKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group_2__0__Impl"


    // $ANTLR start "rule__CoordList__Group_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3171:1: rule__CoordList__Group_2__1 : rule__CoordList__Group_2__1__Impl ;
    public final void rule__CoordList__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3175:1: ( rule__CoordList__Group_2__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3176:2: rule__CoordList__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__CoordList__Group_2__1__Impl_in_rule__CoordList__Group_2__16430);
            rule__CoordList__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group_2__1"


    // $ANTLR start "rule__CoordList__Group_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3182:1: rule__CoordList__Group_2__1__Impl : ( ( rule__CoordList__CoordsAssignment_2_1 ) ) ;
    public final void rule__CoordList__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3186:1: ( ( ( rule__CoordList__CoordsAssignment_2_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3187:1: ( ( rule__CoordList__CoordsAssignment_2_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3187:1: ( ( rule__CoordList__CoordsAssignment_2_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3188:1: ( rule__CoordList__CoordsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getCoordsAssignment_2_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3189:1: ( rule__CoordList__CoordsAssignment_2_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3189:2: rule__CoordList__CoordsAssignment_2_1
            {
            pushFollow(FOLLOW_rule__CoordList__CoordsAssignment_2_1_in_rule__CoordList__Group_2__1__Impl6457);
            rule__CoordList__CoordsAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getCoordsAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__Group_2__1__Impl"


    // $ANTLR start "rule__Geometry__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3203:1: rule__Geometry__Group__0 : rule__Geometry__Group__0__Impl rule__Geometry__Group__1 ;
    public final void rule__Geometry__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3207:1: ( rule__Geometry__Group__0__Impl rule__Geometry__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3208:2: rule__Geometry__Group__0__Impl rule__Geometry__Group__1
            {
            pushFollow(FOLLOW_rule__Geometry__Group__0__Impl_in_rule__Geometry__Group__06491);
            rule__Geometry__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__1_in_rule__Geometry__Group__06494);
            rule__Geometry__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__0"


    // $ANTLR start "rule__Geometry__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3215:1: rule__Geometry__Group__0__Impl : ( () ) ;
    public final void rule__Geometry__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3219:1: ( ( () ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3220:1: ( () )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3220:1: ( () )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3221:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getGeometryAction_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3222:1: ()
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3224:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getGeometryAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__0__Impl"


    // $ANTLR start "rule__Geometry__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3234:1: rule__Geometry__Group__1 : rule__Geometry__Group__1__Impl rule__Geometry__Group__2 ;
    public final void rule__Geometry__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3238:1: ( rule__Geometry__Group__1__Impl rule__Geometry__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3239:2: rule__Geometry__Group__1__Impl rule__Geometry__Group__2
            {
            pushFollow(FOLLOW_rule__Geometry__Group__1__Impl_in_rule__Geometry__Group__16552);
            rule__Geometry__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__2_in_rule__Geometry__Group__16555);
            rule__Geometry__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__1"


    // $ANTLR start "rule__Geometry__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3246:1: rule__Geometry__Group__1__Impl : ( '{' ) ;
    public final void rule__Geometry__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3250:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3251:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3251:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3252:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getLeftCurlyBracketKeyword_1()); 
            }
            match(input,25,FOLLOW_25_in_rule__Geometry__Group__1__Impl6583); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getLeftCurlyBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__1__Impl"


    // $ANTLR start "rule__Geometry__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3265:1: rule__Geometry__Group__2 : rule__Geometry__Group__2__Impl rule__Geometry__Group__3 ;
    public final void rule__Geometry__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3269:1: ( rule__Geometry__Group__2__Impl rule__Geometry__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3270:2: rule__Geometry__Group__2__Impl rule__Geometry__Group__3
            {
            pushFollow(FOLLOW_rule__Geometry__Group__2__Impl_in_rule__Geometry__Group__26614);
            rule__Geometry__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__3_in_rule__Geometry__Group__26617);
            rule__Geometry__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__2"


    // $ANTLR start "rule__Geometry__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3277:1: rule__Geometry__Group__2__Impl : ( '\"type\"' ) ;
    public final void rule__Geometry__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3281:1: ( ( '\"type\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3282:1: ( '\"type\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3282:1: ( '\"type\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3283:1: '\"type\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getTypeKeyword_2()); 
            }
            match(input,27,FOLLOW_27_in_rule__Geometry__Group__2__Impl6645); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getTypeKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__2__Impl"


    // $ANTLR start "rule__Geometry__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3296:1: rule__Geometry__Group__3 : rule__Geometry__Group__3__Impl rule__Geometry__Group__4 ;
    public final void rule__Geometry__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3300:1: ( rule__Geometry__Group__3__Impl rule__Geometry__Group__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3301:2: rule__Geometry__Group__3__Impl rule__Geometry__Group__4
            {
            pushFollow(FOLLOW_rule__Geometry__Group__3__Impl_in_rule__Geometry__Group__36676);
            rule__Geometry__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__4_in_rule__Geometry__Group__36679);
            rule__Geometry__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__3"


    // $ANTLR start "rule__Geometry__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3308:1: rule__Geometry__Group__3__Impl : ( ':' ) ;
    public final void rule__Geometry__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3312:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3313:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3313:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3314:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getColonKeyword_3()); 
            }
            match(input,28,FOLLOW_28_in_rule__Geometry__Group__3__Impl6707); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getColonKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__3__Impl"


    // $ANTLR start "rule__Geometry__Group__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3327:1: rule__Geometry__Group__4 : rule__Geometry__Group__4__Impl rule__Geometry__Group__5 ;
    public final void rule__Geometry__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3331:1: ( rule__Geometry__Group__4__Impl rule__Geometry__Group__5 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3332:2: rule__Geometry__Group__4__Impl rule__Geometry__Group__5
            {
            pushFollow(FOLLOW_rule__Geometry__Group__4__Impl_in_rule__Geometry__Group__46738);
            rule__Geometry__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__5_in_rule__Geometry__Group__46741);
            rule__Geometry__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__4"


    // $ANTLR start "rule__Geometry__Group__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3339:1: rule__Geometry__Group__4__Impl : ( ( rule__Geometry__TypeAssignment_4 ) ) ;
    public final void rule__Geometry__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3343:1: ( ( ( rule__Geometry__TypeAssignment_4 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3344:1: ( ( rule__Geometry__TypeAssignment_4 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3344:1: ( ( rule__Geometry__TypeAssignment_4 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3345:1: ( rule__Geometry__TypeAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getTypeAssignment_4()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3346:1: ( rule__Geometry__TypeAssignment_4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3346:2: rule__Geometry__TypeAssignment_4
            {
            pushFollow(FOLLOW_rule__Geometry__TypeAssignment_4_in_rule__Geometry__Group__4__Impl6768);
            rule__Geometry__TypeAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getTypeAssignment_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__4__Impl"


    // $ANTLR start "rule__Geometry__Group__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3356:1: rule__Geometry__Group__5 : rule__Geometry__Group__5__Impl rule__Geometry__Group__6 ;
    public final void rule__Geometry__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3360:1: ( rule__Geometry__Group__5__Impl rule__Geometry__Group__6 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3361:2: rule__Geometry__Group__5__Impl rule__Geometry__Group__6
            {
            pushFollow(FOLLOW_rule__Geometry__Group__5__Impl_in_rule__Geometry__Group__56798);
            rule__Geometry__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__6_in_rule__Geometry__Group__56801);
            rule__Geometry__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__5"


    // $ANTLR start "rule__Geometry__Group__5__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3368:1: rule__Geometry__Group__5__Impl : ( ',' ) ;
    public final void rule__Geometry__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3372:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3373:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3373:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3374:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getCommaKeyword_5()); 
            }
            match(input,29,FOLLOW_29_in_rule__Geometry__Group__5__Impl6829); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getCommaKeyword_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__5__Impl"


    // $ANTLR start "rule__Geometry__Group__6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3387:1: rule__Geometry__Group__6 : rule__Geometry__Group__6__Impl rule__Geometry__Group__7 ;
    public final void rule__Geometry__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3391:1: ( rule__Geometry__Group__6__Impl rule__Geometry__Group__7 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3392:2: rule__Geometry__Group__6__Impl rule__Geometry__Group__7
            {
            pushFollow(FOLLOW_rule__Geometry__Group__6__Impl_in_rule__Geometry__Group__66860);
            rule__Geometry__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__7_in_rule__Geometry__Group__66863);
            rule__Geometry__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__6"


    // $ANTLR start "rule__Geometry__Group__6__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3399:1: rule__Geometry__Group__6__Impl : ( '\"coordinates\"' ) ;
    public final void rule__Geometry__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3403:1: ( ( '\"coordinates\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3404:1: ( '\"coordinates\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3404:1: ( '\"coordinates\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3405:1: '\"coordinates\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getCoordinatesKeyword_6()); 
            }
            match(input,40,FOLLOW_40_in_rule__Geometry__Group__6__Impl6891); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getCoordinatesKeyword_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__6__Impl"


    // $ANTLR start "rule__Geometry__Group__7"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3418:1: rule__Geometry__Group__7 : rule__Geometry__Group__7__Impl rule__Geometry__Group__8 ;
    public final void rule__Geometry__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3422:1: ( rule__Geometry__Group__7__Impl rule__Geometry__Group__8 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3423:2: rule__Geometry__Group__7__Impl rule__Geometry__Group__8
            {
            pushFollow(FOLLOW_rule__Geometry__Group__7__Impl_in_rule__Geometry__Group__76922);
            rule__Geometry__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__8_in_rule__Geometry__Group__76925);
            rule__Geometry__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__7"


    // $ANTLR start "rule__Geometry__Group__7__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3430:1: rule__Geometry__Group__7__Impl : ( ':' ) ;
    public final void rule__Geometry__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3434:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3435:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3435:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3436:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getColonKeyword_7()); 
            }
            match(input,28,FOLLOW_28_in_rule__Geometry__Group__7__Impl6953); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getColonKeyword_7()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__7__Impl"


    // $ANTLR start "rule__Geometry__Group__8"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3449:1: rule__Geometry__Group__8 : rule__Geometry__Group__8__Impl rule__Geometry__Group__9 ;
    public final void rule__Geometry__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3453:1: ( rule__Geometry__Group__8__Impl rule__Geometry__Group__9 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3454:2: rule__Geometry__Group__8__Impl rule__Geometry__Group__9
            {
            pushFollow(FOLLOW_rule__Geometry__Group__8__Impl_in_rule__Geometry__Group__86984);
            rule__Geometry__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__9_in_rule__Geometry__Group__86987);
            rule__Geometry__Group__9();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__8"


    // $ANTLR start "rule__Geometry__Group__8__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3461:1: rule__Geometry__Group__8__Impl : ( '[' ) ;
    public final void rule__Geometry__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3465:1: ( ( '[' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3466:1: ( '[' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3466:1: ( '[' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3467:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getLeftSquareBracketKeyword_8()); 
            }
            match(input,37,FOLLOW_37_in_rule__Geometry__Group__8__Impl7015); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getLeftSquareBracketKeyword_8()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__8__Impl"


    // $ANTLR start "rule__Geometry__Group__9"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3480:1: rule__Geometry__Group__9 : rule__Geometry__Group__9__Impl rule__Geometry__Group__10 ;
    public final void rule__Geometry__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3484:1: ( rule__Geometry__Group__9__Impl rule__Geometry__Group__10 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3485:2: rule__Geometry__Group__9__Impl rule__Geometry__Group__10
            {
            pushFollow(FOLLOW_rule__Geometry__Group__9__Impl_in_rule__Geometry__Group__97046);
            rule__Geometry__Group__9__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__10_in_rule__Geometry__Group__97049);
            rule__Geometry__Group__10();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__9"


    // $ANTLR start "rule__Geometry__Group__9__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3492:1: rule__Geometry__Group__9__Impl : ( ( rule__Geometry__CoordsAssignment_9 ) ) ;
    public final void rule__Geometry__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3496:1: ( ( ( rule__Geometry__CoordsAssignment_9 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3497:1: ( ( rule__Geometry__CoordsAssignment_9 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3497:1: ( ( rule__Geometry__CoordsAssignment_9 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3498:1: ( rule__Geometry__CoordsAssignment_9 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getCoordsAssignment_9()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3499:1: ( rule__Geometry__CoordsAssignment_9 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3499:2: rule__Geometry__CoordsAssignment_9
            {
            pushFollow(FOLLOW_rule__Geometry__CoordsAssignment_9_in_rule__Geometry__Group__9__Impl7076);
            rule__Geometry__CoordsAssignment_9();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getCoordsAssignment_9()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__9__Impl"


    // $ANTLR start "rule__Geometry__Group__10"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3509:1: rule__Geometry__Group__10 : rule__Geometry__Group__10__Impl rule__Geometry__Group__11 ;
    public final void rule__Geometry__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3513:1: ( rule__Geometry__Group__10__Impl rule__Geometry__Group__11 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3514:2: rule__Geometry__Group__10__Impl rule__Geometry__Group__11
            {
            pushFollow(FOLLOW_rule__Geometry__Group__10__Impl_in_rule__Geometry__Group__107106);
            rule__Geometry__Group__10__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Geometry__Group__11_in_rule__Geometry__Group__107109);
            rule__Geometry__Group__11();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__10"


    // $ANTLR start "rule__Geometry__Group__10__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3521:1: rule__Geometry__Group__10__Impl : ( ']' ) ;
    public final void rule__Geometry__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3525:1: ( ( ']' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3526:1: ( ']' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3526:1: ( ']' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3527:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getRightSquareBracketKeyword_10()); 
            }
            match(input,38,FOLLOW_38_in_rule__Geometry__Group__10__Impl7137); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getRightSquareBracketKeyword_10()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__10__Impl"


    // $ANTLR start "rule__Geometry__Group__11"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3540:1: rule__Geometry__Group__11 : rule__Geometry__Group__11__Impl ;
    public final void rule__Geometry__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3544:1: ( rule__Geometry__Group__11__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3545:2: rule__Geometry__Group__11__Impl
            {
            pushFollow(FOLLOW_rule__Geometry__Group__11__Impl_in_rule__Geometry__Group__117168);
            rule__Geometry__Group__11__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__11"


    // $ANTLR start "rule__Geometry__Group__11__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3551:1: rule__Geometry__Group__11__Impl : ( '}' ) ;
    public final void rule__Geometry__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3555:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3556:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3556:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3557:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getRightCurlyBracketKeyword_11()); 
            }
            match(input,26,FOLLOW_26_in_rule__Geometry__Group__11__Impl7196); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getRightCurlyBracketKeyword_11()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__Group__11__Impl"


    // $ANTLR start "rule__CoordArray__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3594:1: rule__CoordArray__Group__0 : rule__CoordArray__Group__0__Impl rule__CoordArray__Group__1 ;
    public final void rule__CoordArray__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3598:1: ( rule__CoordArray__Group__0__Impl rule__CoordArray__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3599:2: rule__CoordArray__Group__0__Impl rule__CoordArray__Group__1
            {
            pushFollow(FOLLOW_rule__CoordArray__Group__0__Impl_in_rule__CoordArray__Group__07251);
            rule__CoordArray__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordArray__Group__1_in_rule__CoordArray__Group__07254);
            rule__CoordArray__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__0"


    // $ANTLR start "rule__CoordArray__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3606:1: rule__CoordArray__Group__0__Impl : ( () ) ;
    public final void rule__CoordArray__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3610:1: ( ( () ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3611:1: ( () )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3611:1: ( () )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3612:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCoordArrayAction_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3613:1: ()
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3615:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCoordArrayAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__0__Impl"


    // $ANTLR start "rule__CoordArray__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3625:1: rule__CoordArray__Group__1 : rule__CoordArray__Group__1__Impl rule__CoordArray__Group__2 ;
    public final void rule__CoordArray__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3629:1: ( rule__CoordArray__Group__1__Impl rule__CoordArray__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3630:2: rule__CoordArray__Group__1__Impl rule__CoordArray__Group__2
            {
            pushFollow(FOLLOW_rule__CoordArray__Group__1__Impl_in_rule__CoordArray__Group__17312);
            rule__CoordArray__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordArray__Group__2_in_rule__CoordArray__Group__17315);
            rule__CoordArray__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__1"


    // $ANTLR start "rule__CoordArray__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3637:1: rule__CoordArray__Group__1__Impl : ( '[' ) ;
    public final void rule__CoordArray__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3641:1: ( ( '[' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3642:1: ( '[' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3642:1: ( '[' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3643:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getLeftSquareBracketKeyword_1()); 
            }
            match(input,37,FOLLOW_37_in_rule__CoordArray__Group__1__Impl7343); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getLeftSquareBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__1__Impl"


    // $ANTLR start "rule__CoordArray__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3656:1: rule__CoordArray__Group__2 : rule__CoordArray__Group__2__Impl rule__CoordArray__Group__3 ;
    public final void rule__CoordArray__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3660:1: ( rule__CoordArray__Group__2__Impl rule__CoordArray__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3661:2: rule__CoordArray__Group__2__Impl rule__CoordArray__Group__3
            {
            pushFollow(FOLLOW_rule__CoordArray__Group__2__Impl_in_rule__CoordArray__Group__27374);
            rule__CoordArray__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordArray__Group__3_in_rule__CoordArray__Group__27377);
            rule__CoordArray__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__2"


    // $ANTLR start "rule__CoordArray__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3668:1: rule__CoordArray__Group__2__Impl : ( ( rule__CoordArray__Group_2__0 )? ) ;
    public final void rule__CoordArray__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3672:1: ( ( ( rule__CoordArray__Group_2__0 )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3673:1: ( ( rule__CoordArray__Group_2__0 )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3673:1: ( ( rule__CoordArray__Group_2__0 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3674:1: ( rule__CoordArray__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getGroup_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3675:1: ( rule__CoordArray__Group_2__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==37) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3675:2: rule__CoordArray__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__CoordArray__Group_2__0_in_rule__CoordArray__Group__2__Impl7404);
                    rule__CoordArray__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__2__Impl"


    // $ANTLR start "rule__CoordArray__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3685:1: rule__CoordArray__Group__3 : rule__CoordArray__Group__3__Impl ;
    public final void rule__CoordArray__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3689:1: ( rule__CoordArray__Group__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3690:2: rule__CoordArray__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__CoordArray__Group__3__Impl_in_rule__CoordArray__Group__37435);
            rule__CoordArray__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__3"


    // $ANTLR start "rule__CoordArray__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3696:1: rule__CoordArray__Group__3__Impl : ( ']' ) ;
    public final void rule__CoordArray__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3700:1: ( ( ']' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3701:1: ( ']' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3701:1: ( ']' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3702:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getRightSquareBracketKeyword_3()); 
            }
            match(input,38,FOLLOW_38_in_rule__CoordArray__Group__3__Impl7463); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getRightSquareBracketKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group__3__Impl"


    // $ANTLR start "rule__CoordArray__Group_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3723:1: rule__CoordArray__Group_2__0 : rule__CoordArray__Group_2__0__Impl rule__CoordArray__Group_2__1 ;
    public final void rule__CoordArray__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3727:1: ( rule__CoordArray__Group_2__0__Impl rule__CoordArray__Group_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3728:2: rule__CoordArray__Group_2__0__Impl rule__CoordArray__Group_2__1
            {
            pushFollow(FOLLOW_rule__CoordArray__Group_2__0__Impl_in_rule__CoordArray__Group_2__07502);
            rule__CoordArray__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordArray__Group_2__1_in_rule__CoordArray__Group_2__07505);
            rule__CoordArray__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2__0"


    // $ANTLR start "rule__CoordArray__Group_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3735:1: rule__CoordArray__Group_2__0__Impl : ( ( rule__CoordArray__CoordsAssignment_2_0 ) ) ;
    public final void rule__CoordArray__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3739:1: ( ( ( rule__CoordArray__CoordsAssignment_2_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3740:1: ( ( rule__CoordArray__CoordsAssignment_2_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3740:1: ( ( rule__CoordArray__CoordsAssignment_2_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3741:1: ( rule__CoordArray__CoordsAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3742:1: ( rule__CoordArray__CoordsAssignment_2_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3742:2: rule__CoordArray__CoordsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__CoordArray__CoordsAssignment_2_0_in_rule__CoordArray__Group_2__0__Impl7532);
            rule__CoordArray__CoordsAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2__0__Impl"


    // $ANTLR start "rule__CoordArray__Group_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3752:1: rule__CoordArray__Group_2__1 : rule__CoordArray__Group_2__1__Impl ;
    public final void rule__CoordArray__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3756:1: ( rule__CoordArray__Group_2__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3757:2: rule__CoordArray__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__CoordArray__Group_2__1__Impl_in_rule__CoordArray__Group_2__17562);
            rule__CoordArray__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2__1"


    // $ANTLR start "rule__CoordArray__Group_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3763:1: rule__CoordArray__Group_2__1__Impl : ( ( rule__CoordArray__Group_2_1__0 )* ) ;
    public final void rule__CoordArray__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3767:1: ( ( ( rule__CoordArray__Group_2_1__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3768:1: ( ( rule__CoordArray__Group_2_1__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3768:1: ( ( rule__CoordArray__Group_2_1__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3769:1: ( rule__CoordArray__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getGroup_2_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3770:1: ( rule__CoordArray__Group_2_1__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==29) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3770:2: rule__CoordArray__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_rule__CoordArray__Group_2_1__0_in_rule__CoordArray__Group_2__1__Impl7589);
            	    rule__CoordArray__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getGroup_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2__1__Impl"


    // $ANTLR start "rule__CoordArray__Group_2_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3784:1: rule__CoordArray__Group_2_1__0 : rule__CoordArray__Group_2_1__0__Impl rule__CoordArray__Group_2_1__1 ;
    public final void rule__CoordArray__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3788:1: ( rule__CoordArray__Group_2_1__0__Impl rule__CoordArray__Group_2_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3789:2: rule__CoordArray__Group_2_1__0__Impl rule__CoordArray__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__CoordArray__Group_2_1__0__Impl_in_rule__CoordArray__Group_2_1__07624);
            rule__CoordArray__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__CoordArray__Group_2_1__1_in_rule__CoordArray__Group_2_1__07627);
            rule__CoordArray__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2_1__0"


    // $ANTLR start "rule__CoordArray__Group_2_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3796:1: rule__CoordArray__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__CoordArray__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3800:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3801:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3801:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3802:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__CoordArray__Group_2_1__0__Impl7655); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCommaKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2_1__0__Impl"


    // $ANTLR start "rule__CoordArray__Group_2_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3815:1: rule__CoordArray__Group_2_1__1 : rule__CoordArray__Group_2_1__1__Impl ;
    public final void rule__CoordArray__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3819:1: ( rule__CoordArray__Group_2_1__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3820:2: rule__CoordArray__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__CoordArray__Group_2_1__1__Impl_in_rule__CoordArray__Group_2_1__17686);
            rule__CoordArray__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2_1__1"


    // $ANTLR start "rule__CoordArray__Group_2_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3826:1: rule__CoordArray__Group_2_1__1__Impl : ( ( rule__CoordArray__CoordsAssignment_2_1_1 ) ) ;
    public final void rule__CoordArray__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3830:1: ( ( ( rule__CoordArray__CoordsAssignment_2_1_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3831:1: ( ( rule__CoordArray__CoordsAssignment_2_1_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3831:1: ( ( rule__CoordArray__CoordsAssignment_2_1_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3832:1: ( rule__CoordArray__CoordsAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_1_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3833:1: ( rule__CoordArray__CoordsAssignment_2_1_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3833:2: rule__CoordArray__CoordsAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__CoordArray__CoordsAssignment_2_1_1_in_rule__CoordArray__Group_2_1__1__Impl7713);
            rule__CoordArray__CoordsAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__Group_2_1__1__Impl"


    // $ANTLR start "rule__Style__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3847:1: rule__Style__Group__0 : rule__Style__Group__0__Impl rule__Style__Group__1 ;
    public final void rule__Style__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3851:1: ( rule__Style__Group__0__Impl rule__Style__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3852:2: rule__Style__Group__0__Impl rule__Style__Group__1
            {
            pushFollow(FOLLOW_rule__Style__Group__0__Impl_in_rule__Style__Group__07747);
            rule__Style__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Style__Group__1_in_rule__Style__Group__07750);
            rule__Style__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__0"


    // $ANTLR start "rule__Style__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3859:1: rule__Style__Group__0__Impl : ( '{' ) ;
    public final void rule__Style__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3863:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3864:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3864:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3865:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getLeftCurlyBracketKeyword_0()); 
            }
            match(input,25,FOLLOW_25_in_rule__Style__Group__0__Impl7778); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getLeftCurlyBracketKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__0__Impl"


    // $ANTLR start "rule__Style__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3878:1: rule__Style__Group__1 : rule__Style__Group__1__Impl rule__Style__Group__2 ;
    public final void rule__Style__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3882:1: ( rule__Style__Group__1__Impl rule__Style__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3883:2: rule__Style__Group__1__Impl rule__Style__Group__2
            {
            pushFollow(FOLLOW_rule__Style__Group__1__Impl_in_rule__Style__Group__17809);
            rule__Style__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Style__Group__2_in_rule__Style__Group__17812);
            rule__Style__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__1"


    // $ANTLR start "rule__Style__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3890:1: rule__Style__Group__1__Impl : ( ( rule__Style__PropertyAssignment_1 ) ) ;
    public final void rule__Style__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3894:1: ( ( ( rule__Style__PropertyAssignment_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3895:1: ( ( rule__Style__PropertyAssignment_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3895:1: ( ( rule__Style__PropertyAssignment_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3896:1: ( rule__Style__PropertyAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getPropertyAssignment_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3897:1: ( rule__Style__PropertyAssignment_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3897:2: rule__Style__PropertyAssignment_1
            {
            pushFollow(FOLLOW_rule__Style__PropertyAssignment_1_in_rule__Style__Group__1__Impl7839);
            rule__Style__PropertyAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getPropertyAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__1__Impl"


    // $ANTLR start "rule__Style__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3907:1: rule__Style__Group__2 : rule__Style__Group__2__Impl rule__Style__Group__3 ;
    public final void rule__Style__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3911:1: ( rule__Style__Group__2__Impl rule__Style__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3912:2: rule__Style__Group__2__Impl rule__Style__Group__3
            {
            pushFollow(FOLLOW_rule__Style__Group__2__Impl_in_rule__Style__Group__27869);
            rule__Style__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Style__Group__3_in_rule__Style__Group__27872);
            rule__Style__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__2"


    // $ANTLR start "rule__Style__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3919:1: rule__Style__Group__2__Impl : ( ( rule__Style__Group_2__0 )* ) ;
    public final void rule__Style__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3923:1: ( ( ( rule__Style__Group_2__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3924:1: ( ( rule__Style__Group_2__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3924:1: ( ( rule__Style__Group_2__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3925:1: ( rule__Style__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getGroup_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3926:1: ( rule__Style__Group_2__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==29) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3926:2: rule__Style__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__Style__Group_2__0_in_rule__Style__Group__2__Impl7899);
            	    rule__Style__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__2__Impl"


    // $ANTLR start "rule__Style__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3936:1: rule__Style__Group__3 : rule__Style__Group__3__Impl ;
    public final void rule__Style__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3940:1: ( rule__Style__Group__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3941:2: rule__Style__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Style__Group__3__Impl_in_rule__Style__Group__37930);
            rule__Style__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__3"


    // $ANTLR start "rule__Style__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3947:1: rule__Style__Group__3__Impl : ( '}' ) ;
    public final void rule__Style__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3951:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3952:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3952:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3953:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getRightCurlyBracketKeyword_3()); 
            }
            match(input,26,FOLLOW_26_in_rule__Style__Group__3__Impl7958); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getRightCurlyBracketKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group__3__Impl"


    // $ANTLR start "rule__Style__Group_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3974:1: rule__Style__Group_2__0 : rule__Style__Group_2__0__Impl rule__Style__Group_2__1 ;
    public final void rule__Style__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3978:1: ( rule__Style__Group_2__0__Impl rule__Style__Group_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3979:2: rule__Style__Group_2__0__Impl rule__Style__Group_2__1
            {
            pushFollow(FOLLOW_rule__Style__Group_2__0__Impl_in_rule__Style__Group_2__07997);
            rule__Style__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Style__Group_2__1_in_rule__Style__Group_2__08000);
            rule__Style__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group_2__0"


    // $ANTLR start "rule__Style__Group_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3986:1: rule__Style__Group_2__0__Impl : ( ',' ) ;
    public final void rule__Style__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3990:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3991:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3991:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:3992:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getCommaKeyword_2_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__Style__Group_2__0__Impl8028); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getCommaKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group_2__0__Impl"


    // $ANTLR start "rule__Style__Group_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4005:1: rule__Style__Group_2__1 : rule__Style__Group_2__1__Impl ;
    public final void rule__Style__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4009:1: ( rule__Style__Group_2__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4010:2: rule__Style__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Style__Group_2__1__Impl_in_rule__Style__Group_2__18059);
            rule__Style__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group_2__1"


    // $ANTLR start "rule__Style__Group_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4016:1: rule__Style__Group_2__1__Impl : ( ( rule__Style__PropertyAssignment_2_1 ) ) ;
    public final void rule__Style__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4020:1: ( ( ( rule__Style__PropertyAssignment_2_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4021:1: ( ( rule__Style__PropertyAssignment_2_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4021:1: ( ( rule__Style__PropertyAssignment_2_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4022:1: ( rule__Style__PropertyAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getPropertyAssignment_2_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4023:1: ( rule__Style__PropertyAssignment_2_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4023:2: rule__Style__PropertyAssignment_2_1
            {
            pushFollow(FOLLOW_rule__Style__PropertyAssignment_2_1_in_rule__Style__Group_2__1__Impl8086);
            rule__Style__PropertyAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getPropertyAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__Group_2__1__Impl"


    // $ANTLR start "rule__Feature__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4037:1: rule__Feature__Group__0 : rule__Feature__Group__0__Impl rule__Feature__Group__1 ;
    public final void rule__Feature__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4041:1: ( rule__Feature__Group__0__Impl rule__Feature__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4042:2: rule__Feature__Group__0__Impl rule__Feature__Group__1
            {
            pushFollow(FOLLOW_rule__Feature__Group__0__Impl_in_rule__Feature__Group__08120);
            rule__Feature__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__1_in_rule__Feature__Group__08123);
            rule__Feature__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__0"


    // $ANTLR start "rule__Feature__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4049:1: rule__Feature__Group__0__Impl : ( '{' ) ;
    public final void rule__Feature__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4053:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4054:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4054:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4055:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getLeftCurlyBracketKeyword_0()); 
            }
            match(input,25,FOLLOW_25_in_rule__Feature__Group__0__Impl8151); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getLeftCurlyBracketKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__0__Impl"


    // $ANTLR start "rule__Feature__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4068:1: rule__Feature__Group__1 : rule__Feature__Group__1__Impl rule__Feature__Group__2 ;
    public final void rule__Feature__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4072:1: ( rule__Feature__Group__1__Impl rule__Feature__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4073:2: rule__Feature__Group__1__Impl rule__Feature__Group__2
            {
            pushFollow(FOLLOW_rule__Feature__Group__1__Impl_in_rule__Feature__Group__18182);
            rule__Feature__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__2_in_rule__Feature__Group__18185);
            rule__Feature__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__1"


    // $ANTLR start "rule__Feature__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4080:1: rule__Feature__Group__1__Impl : ( '\"geometry\"' ) ;
    public final void rule__Feature__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4084:1: ( ( '\"geometry\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4085:1: ( '\"geometry\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4085:1: ( '\"geometry\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4086:1: '\"geometry\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getGeometryKeyword_1()); 
            }
            match(input,32,FOLLOW_32_in_rule__Feature__Group__1__Impl8213); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getGeometryKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__1__Impl"


    // $ANTLR start "rule__Feature__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4099:1: rule__Feature__Group__2 : rule__Feature__Group__2__Impl rule__Feature__Group__3 ;
    public final void rule__Feature__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4103:1: ( rule__Feature__Group__2__Impl rule__Feature__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4104:2: rule__Feature__Group__2__Impl rule__Feature__Group__3
            {
            pushFollow(FOLLOW_rule__Feature__Group__2__Impl_in_rule__Feature__Group__28244);
            rule__Feature__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__3_in_rule__Feature__Group__28247);
            rule__Feature__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__2"


    // $ANTLR start "rule__Feature__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4111:1: rule__Feature__Group__2__Impl : ( ':' ) ;
    public final void rule__Feature__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4115:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4116:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4116:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4117:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getColonKeyword_2()); 
            }
            match(input,28,FOLLOW_28_in_rule__Feature__Group__2__Impl8275); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getColonKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__2__Impl"


    // $ANTLR start "rule__Feature__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4130:1: rule__Feature__Group__3 : rule__Feature__Group__3__Impl rule__Feature__Group__4 ;
    public final void rule__Feature__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4134:1: ( rule__Feature__Group__3__Impl rule__Feature__Group__4 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4135:2: rule__Feature__Group__3__Impl rule__Feature__Group__4
            {
            pushFollow(FOLLOW_rule__Feature__Group__3__Impl_in_rule__Feature__Group__38306);
            rule__Feature__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__4_in_rule__Feature__Group__38309);
            rule__Feature__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__3"


    // $ANTLR start "rule__Feature__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4142:1: rule__Feature__Group__3__Impl : ( ( rule__Feature__GeometryAssignment_3 ) ) ;
    public final void rule__Feature__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4146:1: ( ( ( rule__Feature__GeometryAssignment_3 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4147:1: ( ( rule__Feature__GeometryAssignment_3 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4147:1: ( ( rule__Feature__GeometryAssignment_3 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4148:1: ( rule__Feature__GeometryAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getGeometryAssignment_3()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4149:1: ( rule__Feature__GeometryAssignment_3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4149:2: rule__Feature__GeometryAssignment_3
            {
            pushFollow(FOLLOW_rule__Feature__GeometryAssignment_3_in_rule__Feature__Group__3__Impl8336);
            rule__Feature__GeometryAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getGeometryAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__3__Impl"


    // $ANTLR start "rule__Feature__Group__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4159:1: rule__Feature__Group__4 : rule__Feature__Group__4__Impl rule__Feature__Group__5 ;
    public final void rule__Feature__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4163:1: ( rule__Feature__Group__4__Impl rule__Feature__Group__5 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4164:2: rule__Feature__Group__4__Impl rule__Feature__Group__5
            {
            pushFollow(FOLLOW_rule__Feature__Group__4__Impl_in_rule__Feature__Group__48366);
            rule__Feature__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__5_in_rule__Feature__Group__48369);
            rule__Feature__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__4"


    // $ANTLR start "rule__Feature__Group__4__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4171:1: rule__Feature__Group__4__Impl : ( ',' ) ;
    public final void rule__Feature__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4175:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4176:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4176:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4177:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getCommaKeyword_4()); 
            }
            match(input,29,FOLLOW_29_in_rule__Feature__Group__4__Impl8397); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getCommaKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__4__Impl"


    // $ANTLR start "rule__Feature__Group__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4190:1: rule__Feature__Group__5 : rule__Feature__Group__5__Impl rule__Feature__Group__6 ;
    public final void rule__Feature__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4194:1: ( rule__Feature__Group__5__Impl rule__Feature__Group__6 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4195:2: rule__Feature__Group__5__Impl rule__Feature__Group__6
            {
            pushFollow(FOLLOW_rule__Feature__Group__5__Impl_in_rule__Feature__Group__58428);
            rule__Feature__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__6_in_rule__Feature__Group__58431);
            rule__Feature__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__5"


    // $ANTLR start "rule__Feature__Group__5__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4202:1: rule__Feature__Group__5__Impl : ( '\"properties\"' ) ;
    public final void rule__Feature__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4206:1: ( ( '\"properties\"' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4207:1: ( '\"properties\"' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4207:1: ( '\"properties\"' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4208:1: '\"properties\"'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getPropertiesKeyword_5()); 
            }
            match(input,34,FOLLOW_34_in_rule__Feature__Group__5__Impl8459); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getPropertiesKeyword_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__5__Impl"


    // $ANTLR start "rule__Feature__Group__6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4221:1: rule__Feature__Group__6 : rule__Feature__Group__6__Impl rule__Feature__Group__7 ;
    public final void rule__Feature__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4225:1: ( rule__Feature__Group__6__Impl rule__Feature__Group__7 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4226:2: rule__Feature__Group__6__Impl rule__Feature__Group__7
            {
            pushFollow(FOLLOW_rule__Feature__Group__6__Impl_in_rule__Feature__Group__68490);
            rule__Feature__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__7_in_rule__Feature__Group__68493);
            rule__Feature__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__6"


    // $ANTLR start "rule__Feature__Group__6__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4233:1: rule__Feature__Group__6__Impl : ( ':' ) ;
    public final void rule__Feature__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4237:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4238:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4238:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4239:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getColonKeyword_6()); 
            }
            match(input,28,FOLLOW_28_in_rule__Feature__Group__6__Impl8521); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getColonKeyword_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__6__Impl"


    // $ANTLR start "rule__Feature__Group__7"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4252:1: rule__Feature__Group__7 : rule__Feature__Group__7__Impl rule__Feature__Group__8 ;
    public final void rule__Feature__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4256:1: ( rule__Feature__Group__7__Impl rule__Feature__Group__8 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4257:2: rule__Feature__Group__7__Impl rule__Feature__Group__8
            {
            pushFollow(FOLLOW_rule__Feature__Group__7__Impl_in_rule__Feature__Group__78552);
            rule__Feature__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Feature__Group__8_in_rule__Feature__Group__78555);
            rule__Feature__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__7"


    // $ANTLR start "rule__Feature__Group__7__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4264:1: rule__Feature__Group__7__Impl : ( ( rule__Feature__PropertiesAssignment_7 ) ) ;
    public final void rule__Feature__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4268:1: ( ( ( rule__Feature__PropertiesAssignment_7 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4269:1: ( ( rule__Feature__PropertiesAssignment_7 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4269:1: ( ( rule__Feature__PropertiesAssignment_7 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4270:1: ( rule__Feature__PropertiesAssignment_7 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getPropertiesAssignment_7()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4271:1: ( rule__Feature__PropertiesAssignment_7 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4271:2: rule__Feature__PropertiesAssignment_7
            {
            pushFollow(FOLLOW_rule__Feature__PropertiesAssignment_7_in_rule__Feature__Group__7__Impl8582);
            rule__Feature__PropertiesAssignment_7();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getPropertiesAssignment_7()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__7__Impl"


    // $ANTLR start "rule__Feature__Group__8"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4281:1: rule__Feature__Group__8 : rule__Feature__Group__8__Impl ;
    public final void rule__Feature__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4285:1: ( rule__Feature__Group__8__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4286:2: rule__Feature__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__Feature__Group__8__Impl_in_rule__Feature__Group__88612);
            rule__Feature__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__8"


    // $ANTLR start "rule__Feature__Group__8__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4292:1: rule__Feature__Group__8__Impl : ( '}' ) ;
    public final void rule__Feature__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4296:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4297:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4297:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4298:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getRightCurlyBracketKeyword_8()); 
            }
            match(input,26,FOLLOW_26_in_rule__Feature__Group__8__Impl8640); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getRightCurlyBracketKeyword_8()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__Group__8__Impl"


    // $ANTLR start "rule__Struct__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4329:1: rule__Struct__Group__0 : rule__Struct__Group__0__Impl rule__Struct__Group__1 ;
    public final void rule__Struct__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4333:1: ( rule__Struct__Group__0__Impl rule__Struct__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4334:2: rule__Struct__Group__0__Impl rule__Struct__Group__1
            {
            pushFollow(FOLLOW_rule__Struct__Group__0__Impl_in_rule__Struct__Group__08689);
            rule__Struct__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Struct__Group__1_in_rule__Struct__Group__08692);
            rule__Struct__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__0"


    // $ANTLR start "rule__Struct__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4341:1: rule__Struct__Group__0__Impl : ( () ) ;
    public final void rule__Struct__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4345:1: ( ( () ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4346:1: ( () )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4346:1: ( () )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4347:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getStructAction_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4348:1: ()
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4350:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getStructAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__0__Impl"


    // $ANTLR start "rule__Struct__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4360:1: rule__Struct__Group__1 : rule__Struct__Group__1__Impl rule__Struct__Group__2 ;
    public final void rule__Struct__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4364:1: ( rule__Struct__Group__1__Impl rule__Struct__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4365:2: rule__Struct__Group__1__Impl rule__Struct__Group__2
            {
            pushFollow(FOLLOW_rule__Struct__Group__1__Impl_in_rule__Struct__Group__18750);
            rule__Struct__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Struct__Group__2_in_rule__Struct__Group__18753);
            rule__Struct__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__1"


    // $ANTLR start "rule__Struct__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4372:1: rule__Struct__Group__1__Impl : ( '{' ) ;
    public final void rule__Struct__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4376:1: ( ( '{' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4377:1: ( '{' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4377:1: ( '{' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4378:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getLeftCurlyBracketKeyword_1()); 
            }
            match(input,25,FOLLOW_25_in_rule__Struct__Group__1__Impl8781); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getLeftCurlyBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__1__Impl"


    // $ANTLR start "rule__Struct__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4391:1: rule__Struct__Group__2 : rule__Struct__Group__2__Impl rule__Struct__Group__3 ;
    public final void rule__Struct__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4395:1: ( rule__Struct__Group__2__Impl rule__Struct__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4396:2: rule__Struct__Group__2__Impl rule__Struct__Group__3
            {
            pushFollow(FOLLOW_rule__Struct__Group__2__Impl_in_rule__Struct__Group__28812);
            rule__Struct__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Struct__Group__3_in_rule__Struct__Group__28815);
            rule__Struct__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__2"


    // $ANTLR start "rule__Struct__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4403:1: rule__Struct__Group__2__Impl : ( ( rule__Struct__Group_2__0 )? ) ;
    public final void rule__Struct__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4407:1: ( ( ( rule__Struct__Group_2__0 )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4408:1: ( ( rule__Struct__Group_2__0 )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4408:1: ( ( rule__Struct__Group_2__0 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4409:1: ( rule__Struct__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getGroup_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4410:1: ( rule__Struct__Group_2__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_STRING) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4410:2: rule__Struct__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Struct__Group_2__0_in_rule__Struct__Group__2__Impl8842);
                    rule__Struct__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__2__Impl"


    // $ANTLR start "rule__Struct__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4420:1: rule__Struct__Group__3 : rule__Struct__Group__3__Impl ;
    public final void rule__Struct__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4424:1: ( rule__Struct__Group__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4425:2: rule__Struct__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Struct__Group__3__Impl_in_rule__Struct__Group__38873);
            rule__Struct__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__3"


    // $ANTLR start "rule__Struct__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4431:1: rule__Struct__Group__3__Impl : ( '}' ) ;
    public final void rule__Struct__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4435:1: ( ( '}' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4436:1: ( '}' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4436:1: ( '}' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4437:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getRightCurlyBracketKeyword_3()); 
            }
            match(input,26,FOLLOW_26_in_rule__Struct__Group__3__Impl8901); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getRightCurlyBracketKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group__3__Impl"


    // $ANTLR start "rule__Struct__Group_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4458:1: rule__Struct__Group_2__0 : rule__Struct__Group_2__0__Impl rule__Struct__Group_2__1 ;
    public final void rule__Struct__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4462:1: ( rule__Struct__Group_2__0__Impl rule__Struct__Group_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4463:2: rule__Struct__Group_2__0__Impl rule__Struct__Group_2__1
            {
            pushFollow(FOLLOW_rule__Struct__Group_2__0__Impl_in_rule__Struct__Group_2__08940);
            rule__Struct__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Struct__Group_2__1_in_rule__Struct__Group_2__08943);
            rule__Struct__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2__0"


    // $ANTLR start "rule__Struct__Group_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4470:1: rule__Struct__Group_2__0__Impl : ( ( rule__Struct__PropertyAssignment_2_0 ) ) ;
    public final void rule__Struct__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4474:1: ( ( ( rule__Struct__PropertyAssignment_2_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4475:1: ( ( rule__Struct__PropertyAssignment_2_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4475:1: ( ( rule__Struct__PropertyAssignment_2_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4476:1: ( rule__Struct__PropertyAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getPropertyAssignment_2_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4477:1: ( rule__Struct__PropertyAssignment_2_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4477:2: rule__Struct__PropertyAssignment_2_0
            {
            pushFollow(FOLLOW_rule__Struct__PropertyAssignment_2_0_in_rule__Struct__Group_2__0__Impl8970);
            rule__Struct__PropertyAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getPropertyAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2__0__Impl"


    // $ANTLR start "rule__Struct__Group_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4487:1: rule__Struct__Group_2__1 : rule__Struct__Group_2__1__Impl ;
    public final void rule__Struct__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4491:1: ( rule__Struct__Group_2__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4492:2: rule__Struct__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Struct__Group_2__1__Impl_in_rule__Struct__Group_2__19000);
            rule__Struct__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2__1"


    // $ANTLR start "rule__Struct__Group_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4498:1: rule__Struct__Group_2__1__Impl : ( ( rule__Struct__Group_2_1__0 )* ) ;
    public final void rule__Struct__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4502:1: ( ( ( rule__Struct__Group_2_1__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4503:1: ( ( rule__Struct__Group_2_1__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4503:1: ( ( rule__Struct__Group_2_1__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4504:1: ( rule__Struct__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getGroup_2_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4505:1: ( rule__Struct__Group_2_1__0 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==29) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4505:2: rule__Struct__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Struct__Group_2_1__0_in_rule__Struct__Group_2__1__Impl9027);
            	    rule__Struct__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getGroup_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2__1__Impl"


    // $ANTLR start "rule__Struct__Group_2_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4519:1: rule__Struct__Group_2_1__0 : rule__Struct__Group_2_1__0__Impl rule__Struct__Group_2_1__1 ;
    public final void rule__Struct__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4523:1: ( rule__Struct__Group_2_1__0__Impl rule__Struct__Group_2_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4524:2: rule__Struct__Group_2_1__0__Impl rule__Struct__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__Struct__Group_2_1__0__Impl_in_rule__Struct__Group_2_1__09062);
            rule__Struct__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Struct__Group_2_1__1_in_rule__Struct__Group_2_1__09065);
            rule__Struct__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2_1__0"


    // $ANTLR start "rule__Struct__Group_2_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4531:1: rule__Struct__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__Struct__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4535:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4536:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4536:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4537:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__Struct__Group_2_1__0__Impl9093); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getCommaKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2_1__0__Impl"


    // $ANTLR start "rule__Struct__Group_2_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4550:1: rule__Struct__Group_2_1__1 : rule__Struct__Group_2_1__1__Impl ;
    public final void rule__Struct__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4554:1: ( rule__Struct__Group_2_1__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4555:2: rule__Struct__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Struct__Group_2_1__1__Impl_in_rule__Struct__Group_2_1__19124);
            rule__Struct__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2_1__1"


    // $ANTLR start "rule__Struct__Group_2_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4561:1: rule__Struct__Group_2_1__1__Impl : ( ( rule__Struct__PropertyAssignment_2_1_1 ) ) ;
    public final void rule__Struct__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4565:1: ( ( ( rule__Struct__PropertyAssignment_2_1_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4566:1: ( ( rule__Struct__PropertyAssignment_2_1_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4566:1: ( ( rule__Struct__PropertyAssignment_2_1_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4567:1: ( rule__Struct__PropertyAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getPropertyAssignment_2_1_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4568:1: ( rule__Struct__PropertyAssignment_2_1_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4568:2: rule__Struct__PropertyAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__Struct__PropertyAssignment_2_1_1_in_rule__Struct__Group_2_1__1__Impl9151);
            rule__Struct__PropertyAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getPropertyAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__Group_2_1__1__Impl"


    // $ANTLR start "rule__Property__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4582:1: rule__Property__Group__0 : rule__Property__Group__0__Impl rule__Property__Group__1 ;
    public final void rule__Property__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4586:1: ( rule__Property__Group__0__Impl rule__Property__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4587:2: rule__Property__Group__0__Impl rule__Property__Group__1
            {
            pushFollow(FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__09185);
            rule__Property__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Property__Group__1_in_rule__Property__Group__09188);
            rule__Property__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__0"


    // $ANTLR start "rule__Property__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4594:1: rule__Property__Group__0__Impl : ( ( rule__Property__NameAssignment_0 ) ) ;
    public final void rule__Property__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4598:1: ( ( ( rule__Property__NameAssignment_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4599:1: ( ( rule__Property__NameAssignment_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4599:1: ( ( rule__Property__NameAssignment_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4600:1: ( rule__Property__NameAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getNameAssignment_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4601:1: ( rule__Property__NameAssignment_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4601:2: rule__Property__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__Property__NameAssignment_0_in_rule__Property__Group__0__Impl9215);
            rule__Property__NameAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getNameAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__0__Impl"


    // $ANTLR start "rule__Property__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4611:1: rule__Property__Group__1 : rule__Property__Group__1__Impl rule__Property__Group__2 ;
    public final void rule__Property__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4615:1: ( rule__Property__Group__1__Impl rule__Property__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4616:2: rule__Property__Group__1__Impl rule__Property__Group__2
            {
            pushFollow(FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__19245);
            rule__Property__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Property__Group__2_in_rule__Property__Group__19248);
            rule__Property__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__1"


    // $ANTLR start "rule__Property__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4623:1: rule__Property__Group__1__Impl : ( ':' ) ;
    public final void rule__Property__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4627:1: ( ( ':' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4628:1: ( ':' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4628:1: ( ':' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4629:1: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getColonKeyword_1()); 
            }
            match(input,28,FOLLOW_28_in_rule__Property__Group__1__Impl9276); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getColonKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__1__Impl"


    // $ANTLR start "rule__Property__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4642:1: rule__Property__Group__2 : rule__Property__Group__2__Impl ;
    public final void rule__Property__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4646:1: ( rule__Property__Group__2__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4647:2: rule__Property__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__29307);
            rule__Property__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__2"


    // $ANTLR start "rule__Property__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4653:1: rule__Property__Group__2__Impl : ( ( rule__Property__ValueAssignment_2 ) ) ;
    public final void rule__Property__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4657:1: ( ( ( rule__Property__ValueAssignment_2 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4658:1: ( ( rule__Property__ValueAssignment_2 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4658:1: ( ( rule__Property__ValueAssignment_2 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4659:1: ( rule__Property__ValueAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getValueAssignment_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4660:1: ( rule__Property__ValueAssignment_2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4660:2: rule__Property__ValueAssignment_2
            {
            pushFollow(FOLLOW_rule__Property__ValueAssignment_2_in_rule__Property__Group__2__Impl9334);
            rule__Property__ValueAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getValueAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__2__Impl"


    // $ANTLR start "rule__Array__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4676:1: rule__Array__Group__0 : rule__Array__Group__0__Impl rule__Array__Group__1 ;
    public final void rule__Array__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4680:1: ( rule__Array__Group__0__Impl rule__Array__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4681:2: rule__Array__Group__0__Impl rule__Array__Group__1
            {
            pushFollow(FOLLOW_rule__Array__Group__0__Impl_in_rule__Array__Group__09370);
            rule__Array__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Array__Group__1_in_rule__Array__Group__09373);
            rule__Array__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__0"


    // $ANTLR start "rule__Array__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4688:1: rule__Array__Group__0__Impl : ( () ) ;
    public final void rule__Array__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4692:1: ( ( () ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4693:1: ( () )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4693:1: ( () )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4694:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getArrayAction_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4695:1: ()
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4697:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getArrayAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__0__Impl"


    // $ANTLR start "rule__Array__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4707:1: rule__Array__Group__1 : rule__Array__Group__1__Impl rule__Array__Group__2 ;
    public final void rule__Array__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4711:1: ( rule__Array__Group__1__Impl rule__Array__Group__2 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4712:2: rule__Array__Group__1__Impl rule__Array__Group__2
            {
            pushFollow(FOLLOW_rule__Array__Group__1__Impl_in_rule__Array__Group__19431);
            rule__Array__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Array__Group__2_in_rule__Array__Group__19434);
            rule__Array__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__1"


    // $ANTLR start "rule__Array__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4719:1: rule__Array__Group__1__Impl : ( '[' ) ;
    public final void rule__Array__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4723:1: ( ( '[' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4724:1: ( '[' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4724:1: ( '[' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4725:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getLeftSquareBracketKeyword_1()); 
            }
            match(input,37,FOLLOW_37_in_rule__Array__Group__1__Impl9462); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getLeftSquareBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__1__Impl"


    // $ANTLR start "rule__Array__Group__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4738:1: rule__Array__Group__2 : rule__Array__Group__2__Impl rule__Array__Group__3 ;
    public final void rule__Array__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4742:1: ( rule__Array__Group__2__Impl rule__Array__Group__3 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4743:2: rule__Array__Group__2__Impl rule__Array__Group__3
            {
            pushFollow(FOLLOW_rule__Array__Group__2__Impl_in_rule__Array__Group__29493);
            rule__Array__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Array__Group__3_in_rule__Array__Group__29496);
            rule__Array__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__2"


    // $ANTLR start "rule__Array__Group__2__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4750:1: rule__Array__Group__2__Impl : ( ( rule__Array__Group_2__0 )? ) ;
    public final void rule__Array__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4754:1: ( ( ( rule__Array__Group_2__0 )? ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4755:1: ( ( rule__Array__Group_2__0 )? )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4755:1: ( ( rule__Array__Group_2__0 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4756:1: ( rule__Array__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getGroup_2()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4757:1: ( rule__Array__Group_2__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=RULE_INT && LA27_0<=RULE_STRING)||LA27_0==25||LA27_0==37||LA27_0==41) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4757:2: rule__Array__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Array__Group_2__0_in_rule__Array__Group__2__Impl9523);
                    rule__Array__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__2__Impl"


    // $ANTLR start "rule__Array__Group__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4767:1: rule__Array__Group__3 : rule__Array__Group__3__Impl ;
    public final void rule__Array__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4771:1: ( rule__Array__Group__3__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4772:2: rule__Array__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Array__Group__3__Impl_in_rule__Array__Group__39554);
            rule__Array__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__3"


    // $ANTLR start "rule__Array__Group__3__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4778:1: rule__Array__Group__3__Impl : ( ']' ) ;
    public final void rule__Array__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4782:1: ( ( ']' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4783:1: ( ']' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4783:1: ( ']' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4784:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getRightSquareBracketKeyword_3()); 
            }
            match(input,38,FOLLOW_38_in_rule__Array__Group__3__Impl9582); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getRightSquareBracketKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group__3__Impl"


    // $ANTLR start "rule__Array__Group_2__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4805:1: rule__Array__Group_2__0 : rule__Array__Group_2__0__Impl rule__Array__Group_2__1 ;
    public final void rule__Array__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4809:1: ( rule__Array__Group_2__0__Impl rule__Array__Group_2__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4810:2: rule__Array__Group_2__0__Impl rule__Array__Group_2__1
            {
            pushFollow(FOLLOW_rule__Array__Group_2__0__Impl_in_rule__Array__Group_2__09621);
            rule__Array__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Array__Group_2__1_in_rule__Array__Group_2__09624);
            rule__Array__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2__0"


    // $ANTLR start "rule__Array__Group_2__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4817:1: rule__Array__Group_2__0__Impl : ( ( rule__Array__ValuesAssignment_2_0 ) ) ;
    public final void rule__Array__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4821:1: ( ( ( rule__Array__ValuesAssignment_2_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4822:1: ( ( rule__Array__ValuesAssignment_2_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4822:1: ( ( rule__Array__ValuesAssignment_2_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4823:1: ( rule__Array__ValuesAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getValuesAssignment_2_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4824:1: ( rule__Array__ValuesAssignment_2_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4824:2: rule__Array__ValuesAssignment_2_0
            {
            pushFollow(FOLLOW_rule__Array__ValuesAssignment_2_0_in_rule__Array__Group_2__0__Impl9651);
            rule__Array__ValuesAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getValuesAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2__0__Impl"


    // $ANTLR start "rule__Array__Group_2__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4834:1: rule__Array__Group_2__1 : rule__Array__Group_2__1__Impl ;
    public final void rule__Array__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4838:1: ( rule__Array__Group_2__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4839:2: rule__Array__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Array__Group_2__1__Impl_in_rule__Array__Group_2__19681);
            rule__Array__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2__1"


    // $ANTLR start "rule__Array__Group_2__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4845:1: rule__Array__Group_2__1__Impl : ( ( rule__Array__Group_2_1__0 )* ) ;
    public final void rule__Array__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4849:1: ( ( ( rule__Array__Group_2_1__0 )* ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4850:1: ( ( rule__Array__Group_2_1__0 )* )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4850:1: ( ( rule__Array__Group_2_1__0 )* )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4851:1: ( rule__Array__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getGroup_2_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4852:1: ( rule__Array__Group_2_1__0 )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==29) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4852:2: rule__Array__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Array__Group_2_1__0_in_rule__Array__Group_2__1__Impl9708);
            	    rule__Array__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getGroup_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2__1__Impl"


    // $ANTLR start "rule__Array__Group_2_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4866:1: rule__Array__Group_2_1__0 : rule__Array__Group_2_1__0__Impl rule__Array__Group_2_1__1 ;
    public final void rule__Array__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4870:1: ( rule__Array__Group_2_1__0__Impl rule__Array__Group_2_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4871:2: rule__Array__Group_2_1__0__Impl rule__Array__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__Array__Group_2_1__0__Impl_in_rule__Array__Group_2_1__09743);
            rule__Array__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Array__Group_2_1__1_in_rule__Array__Group_2_1__09746);
            rule__Array__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2_1__0"


    // $ANTLR start "rule__Array__Group_2_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4878:1: rule__Array__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__Array__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4882:1: ( ( ',' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4883:1: ( ',' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4883:1: ( ',' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4884:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__Array__Group_2_1__0__Impl9774); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getCommaKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2_1__0__Impl"


    // $ANTLR start "rule__Array__Group_2_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4897:1: rule__Array__Group_2_1__1 : rule__Array__Group_2_1__1__Impl ;
    public final void rule__Array__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4901:1: ( rule__Array__Group_2_1__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4902:2: rule__Array__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Array__Group_2_1__1__Impl_in_rule__Array__Group_2_1__19805);
            rule__Array__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2_1__1"


    // $ANTLR start "rule__Array__Group_2_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4908:1: rule__Array__Group_2_1__1__Impl : ( ( rule__Array__ValuesAssignment_2_1_1 ) ) ;
    public final void rule__Array__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4912:1: ( ( ( rule__Array__ValuesAssignment_2_1_1 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4913:1: ( ( rule__Array__ValuesAssignment_2_1_1 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4913:1: ( ( rule__Array__ValuesAssignment_2_1_1 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4914:1: ( rule__Array__ValuesAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getValuesAssignment_2_1_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4915:1: ( rule__Array__ValuesAssignment_2_1_1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4915:2: rule__Array__ValuesAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__Array__ValuesAssignment_2_1_1_in_rule__Array__Group_2_1__1__Impl9832);
            rule__Array__ValuesAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getValuesAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__Group_2_1__1__Impl"


    // $ANTLR start "rule__NINT__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4929:1: rule__NINT__Group__0 : rule__NINT__Group__0__Impl rule__NINT__Group__1 ;
    public final void rule__NINT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4933:1: ( rule__NINT__Group__0__Impl rule__NINT__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4934:2: rule__NINT__Group__0__Impl rule__NINT__Group__1
            {
            pushFollow(FOLLOW_rule__NINT__Group__0__Impl_in_rule__NINT__Group__09866);
            rule__NINT__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__NINT__Group__1_in_rule__NINT__Group__09869);
            rule__NINT__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NINT__Group__0"


    // $ANTLR start "rule__NINT__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4941:1: rule__NINT__Group__0__Impl : ( '-' ) ;
    public final void rule__NINT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4945:1: ( ( '-' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4946:1: ( '-' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4946:1: ( '-' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4947:1: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNINTAccess().getHyphenMinusKeyword_0()); 
            }
            match(input,41,FOLLOW_41_in_rule__NINT__Group__0__Impl9897); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNINTAccess().getHyphenMinusKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NINT__Group__0__Impl"


    // $ANTLR start "rule__NINT__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4960:1: rule__NINT__Group__1 : rule__NINT__Group__1__Impl ;
    public final void rule__NINT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4964:1: ( rule__NINT__Group__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4965:2: rule__NINT__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__NINT__Group__1__Impl_in_rule__NINT__Group__19928);
            rule__NINT__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NINT__Group__1"


    // $ANTLR start "rule__NINT__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4971:1: rule__NINT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__NINT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4975:1: ( ( RULE_INT ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4976:1: ( RULE_INT )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4976:1: ( RULE_INT )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4977:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNINTAccess().getINTTerminalRuleCall_1()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__NINT__Group__1__Impl9955); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNINTAccess().getINTTerminalRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NINT__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4992:1: rule__DOUBLE__Group__0 : rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 ;
    public final void rule__DOUBLE__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4996:1: ( rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:4997:2: rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group__0__Impl_in_rule__DOUBLE__Group__09988);
            rule__DOUBLE__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__DOUBLE__Group__1_in_rule__DOUBLE__Group__09991);
            rule__DOUBLE__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0"


    // $ANTLR start "rule__DOUBLE__Group__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5004:1: rule__DOUBLE__Group__0__Impl : ( ( rule__DOUBLE__Alternatives_0 ) ) ;
    public final void rule__DOUBLE__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5008:1: ( ( ( rule__DOUBLE__Alternatives_0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5009:1: ( ( rule__DOUBLE__Alternatives_0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5009:1: ( ( rule__DOUBLE__Alternatives_0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5010:1: ( rule__DOUBLE__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLEAccess().getAlternatives_0()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5011:1: ( rule__DOUBLE__Alternatives_0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5011:2: rule__DOUBLE__Alternatives_0
            {
            pushFollow(FOLLOW_rule__DOUBLE__Alternatives_0_in_rule__DOUBLE__Group__0__Impl10018);
            rule__DOUBLE__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLEAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5021:1: rule__DOUBLE__Group__1 : rule__DOUBLE__Group__1__Impl ;
    public final void rule__DOUBLE__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5025:1: ( rule__DOUBLE__Group__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5026:2: rule__DOUBLE__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group__1__Impl_in_rule__DOUBLE__Group__110048);
            rule__DOUBLE__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1"


    // $ANTLR start "rule__DOUBLE__Group__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5032:1: rule__DOUBLE__Group__1__Impl : ( ( rule__DOUBLE__Group_1__0 ) ) ;
    public final void rule__DOUBLE__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5036:1: ( ( ( rule__DOUBLE__Group_1__0 ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5037:1: ( ( rule__DOUBLE__Group_1__0 ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5037:1: ( ( rule__DOUBLE__Group_1__0 ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5038:1: ( rule__DOUBLE__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLEAccess().getGroup_1()); 
            }
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5039:1: ( rule__DOUBLE__Group_1__0 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5039:2: rule__DOUBLE__Group_1__0
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group_1__0_in_rule__DOUBLE__Group__1__Impl10075);
            rule__DOUBLE__Group_1__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLEAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5053:1: rule__DOUBLE__Group_1__0 : rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1 ;
    public final void rule__DOUBLE__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5057:1: ( rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1 )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5058:2: rule__DOUBLE__Group_1__0__Impl rule__DOUBLE__Group_1__1
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group_1__0__Impl_in_rule__DOUBLE__Group_1__010109);
            rule__DOUBLE__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__DOUBLE__Group_1__1_in_rule__DOUBLE__Group_1__010112);
            rule__DOUBLE__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__0"


    // $ANTLR start "rule__DOUBLE__Group_1__0__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5065:1: rule__DOUBLE__Group_1__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5069:1: ( ( '.' ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5070:1: ( '.' )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5070:1: ( '.' )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5071:1: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLEAccess().getFullStopKeyword_1_0()); 
            }
            match(input,42,FOLLOW_42_in_rule__DOUBLE__Group_1__0__Impl10140); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLEAccess().getFullStopKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5084:1: rule__DOUBLE__Group_1__1 : rule__DOUBLE__Group_1__1__Impl ;
    public final void rule__DOUBLE__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5088:1: ( rule__DOUBLE__Group_1__1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5089:2: rule__DOUBLE__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__DOUBLE__Group_1__1__Impl_in_rule__DOUBLE__Group_1__110171);
            rule__DOUBLE__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__1"


    // $ANTLR start "rule__DOUBLE__Group_1__1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5095:1: rule__DOUBLE__Group_1__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5099:1: ( ( RULE_INT ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5100:1: ( RULE_INT )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5100:1: ( RULE_INT )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5101:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1_1()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__DOUBLE__Group_1__1__Impl10198); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_1__1__Impl"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5117:1: rule__GeoJSON__UnorderedGroup_1 : rule__GeoJSON__UnorderedGroup_1__0 {...}?;
    public final void rule__GeoJSON__UnorderedGroup_1() throws RecognitionException {

            	int stackSize = keepStackSize();
        		getUnorderedGroupHelper().enter(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1());
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5122:1: ( rule__GeoJSON__UnorderedGroup_1__0 {...}?)
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5123:2: rule__GeoJSON__UnorderedGroup_1__0 {...}?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__0_in_rule__GeoJSON__UnorderedGroup_110232);
            rule__GeoJSON__UnorderedGroup_1__0();

            state._fsp--;
            if (state.failed) return ;
            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1()) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1", "getUnorderedGroupHelper().canLeave(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1())");
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	getUnorderedGroupHelper().leave(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__Impl"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5134:1: rule__GeoJSON__UnorderedGroup_1__Impl : ( ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) ) | ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) ) ) ;
    public final void rule__GeoJSON__UnorderedGroup_1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        		boolean selected = false;
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5139:1: ( ( ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) ) | ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) ) ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5140:3: ( ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) ) | ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) ) )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5140:3: ( ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) ) | ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) ) | ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) ) )
            int alt30=8;
            int LA30_0 = input.LA(1);

            if ( LA30_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {
                alt30=1;
            }
            else if ( LA30_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {
                alt30=2;
            }
            else if ( LA30_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {
                alt30=3;
            }
            else if ( LA30_0 >=32 && LA30_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {
                alt30=4;
            }
            else if ( LA30_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {
                alt30=5;
            }
            else if ( LA30_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {
                alt30=6;
            }
            else if ( LA30_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {
                alt30=7;
            }
            else if ( LA30_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {
                alt30=8;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5142:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5142:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5143:5: {...}? => ( ( ( rule__GeoJSON__Group_1_0__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5143:104: ( ( ( rule__GeoJSON__Group_1_0__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5144:6: ( ( rule__GeoJSON__Group_1_0__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5150:6: ( ( rule__GeoJSON__Group_1_0__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5152:7: ( rule__GeoJSON__Group_1_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_0()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5153:7: ( rule__GeoJSON__Group_1_0__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5153:8: rule__GeoJSON__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_0__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10321);
                    rule__GeoJSON__Group_1_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_0()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5159:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5159:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5160:5: {...}? => ( ( ( rule__GeoJSON__Group_1_1__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5160:104: ( ( ( rule__GeoJSON__Group_1_1__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5161:6: ( ( rule__GeoJSON__Group_1_1__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5167:6: ( ( rule__GeoJSON__Group_1_1__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5169:7: ( rule__GeoJSON__Group_1_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_1()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5170:7: ( rule__GeoJSON__Group_1_1__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5170:8: rule__GeoJSON__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_1__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10412);
                    rule__GeoJSON__Group_1_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_1()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5176:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5176:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5177:5: {...}? => ( ( ( rule__GeoJSON__Group_1_2__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5177:104: ( ( ( rule__GeoJSON__Group_1_2__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5178:6: ( ( rule__GeoJSON__Group_1_2__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5184:6: ( ( rule__GeoJSON__Group_1_2__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5186:7: ( rule__GeoJSON__Group_1_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_2()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5187:7: ( rule__GeoJSON__Group_1_2__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5187:8: rule__GeoJSON__Group_1_2__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_2__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10503);
                    rule__GeoJSON__Group_1_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_2()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5193:4: ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5193:4: ({...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5194:5: {...}? => ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5194:104: ( ( ( rule__GeoJSON__Alternatives_1_3 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5195:6: ( ( rule__GeoJSON__Alternatives_1_3 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5201:6: ( ( rule__GeoJSON__Alternatives_1_3 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5203:7: ( rule__GeoJSON__Alternatives_1_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getAlternatives_1_3()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5204:7: ( rule__GeoJSON__Alternatives_1_3 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5204:8: rule__GeoJSON__Alternatives_1_3
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Alternatives_1_3_in_rule__GeoJSON__UnorderedGroup_1__Impl10594);
                    rule__GeoJSON__Alternatives_1_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getAlternatives_1_3()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5210:4: ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5210:4: ({...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5211:5: {...}? => ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5211:104: ( ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5212:6: ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5218:6: ( ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5219:6: ( ( rule__GeoJSON__Group_1_4__0 ) ) ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5219:6: ( ( rule__GeoJSON__Group_1_4__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5220:7: ( rule__GeoJSON__Group_1_4__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_4()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5221:7: ( rule__GeoJSON__Group_1_4__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5221:8: rule__GeoJSON__Group_1_4__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10686);
                    rule__GeoJSON__Group_1_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_4()); 
                    }

                    }

                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5224:6: ( ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )* )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5225:7: ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )*
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_4()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5226:7: ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )*
                    loop29:
                    do {
                        int alt29=2;
                        alt29 = dfa29.predict(input);
                        switch (alt29) {
                    	case 1 :
                    	    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5226:8: ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0
                    	    {
                    	    pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10730);
                    	    rule__GeoJSON__Group_1_4__0();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_4()); 
                    }

                    }


                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5232:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5232:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5233:5: {...}? => ( ( ( rule__GeoJSON__Group_1_5__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5233:104: ( ( ( rule__GeoJSON__Group_1_5__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5234:6: ( ( rule__GeoJSON__Group_1_5__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5240:6: ( ( rule__GeoJSON__Group_1_5__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5242:7: ( rule__GeoJSON__Group_1_5__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_5()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5243:7: ( rule__GeoJSON__Group_1_5__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5243:8: rule__GeoJSON__Group_1_5__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_5__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10828);
                    rule__GeoJSON__Group_1_5__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_5()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5249:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5249:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5250:5: {...}? => ( ( ( rule__GeoJSON__Group_1_6__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5250:104: ( ( ( rule__GeoJSON__Group_1_6__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5251:6: ( ( rule__GeoJSON__Group_1_6__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5257:6: ( ( rule__GeoJSON__Group_1_6__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5259:7: ( rule__GeoJSON__Group_1_6__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_6()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5260:7: ( rule__GeoJSON__Group_1_6__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5260:8: rule__GeoJSON__Group_1_6__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_6__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10919);
                    rule__GeoJSON__Group_1_6__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_6()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5266:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) )
                    {
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5266:4: ({...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5267:5: {...}? => ( ( ( rule__GeoJSON__Group_1_7__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__GeoJSON__UnorderedGroup_1__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7)");
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5267:104: ( ( ( rule__GeoJSON__Group_1_7__0 ) ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5268:6: ( ( rule__GeoJSON__Group_1_7__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7);
                    selected = true;
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5274:6: ( ( rule__GeoJSON__Group_1_7__0 ) )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5276:7: ( rule__GeoJSON__Group_1_7__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGeoJSONAccess().getGroup_1_7()); 
                    }
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5277:7: ( rule__GeoJSON__Group_1_7__0 )
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5277:8: rule__GeoJSON__Group_1_7__0
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__Group_1_7__0_in_rule__GeoJSON__UnorderedGroup_1__Impl11010);
                    rule__GeoJSON__Group_1_7__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGeoJSONAccess().getGroup_1_7()); 
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	if (selected)
            		getUnorderedGroupHelper().returnFromSelection(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__Impl"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5292:1: rule__GeoJSON__UnorderedGroup_1__0 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__1 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5296:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__1 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5297:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__1 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__011069);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5298:2: ( rule__GeoJSON__UnorderedGroup_1__1 )?
            int alt31=2;
            alt31 = dfa31.predict(input);
            switch (alt31) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5298:2: rule__GeoJSON__UnorderedGroup_1__1
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__1_in_rule__GeoJSON__UnorderedGroup_1__011072);
                    rule__GeoJSON__UnorderedGroup_1__1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__0"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5305:1: rule__GeoJSON__UnorderedGroup_1__1 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__2 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5309:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__2 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5310:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__2 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__111097);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5311:2: ( rule__GeoJSON__UnorderedGroup_1__2 )?
            int alt32=2;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5311:2: rule__GeoJSON__UnorderedGroup_1__2
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__2_in_rule__GeoJSON__UnorderedGroup_1__111100);
                    rule__GeoJSON__UnorderedGroup_1__2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__1"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5318:1: rule__GeoJSON__UnorderedGroup_1__2 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__3 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5322:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__3 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5323:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__3 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__211125);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5324:2: ( rule__GeoJSON__UnorderedGroup_1__3 )?
            int alt33=2;
            alt33 = dfa33.predict(input);
            switch (alt33) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5324:2: rule__GeoJSON__UnorderedGroup_1__3
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__3_in_rule__GeoJSON__UnorderedGroup_1__211128);
                    rule__GeoJSON__UnorderedGroup_1__3();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__2"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5331:1: rule__GeoJSON__UnorderedGroup_1__3 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__4 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5335:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__4 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5336:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__4 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__311153);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5337:2: ( rule__GeoJSON__UnorderedGroup_1__4 )?
            int alt34=2;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5337:2: rule__GeoJSON__UnorderedGroup_1__4
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__4_in_rule__GeoJSON__UnorderedGroup_1__311156);
                    rule__GeoJSON__UnorderedGroup_1__4();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__3"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5344:1: rule__GeoJSON__UnorderedGroup_1__4 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__5 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5348:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__5 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5349:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__5 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__411181);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5350:2: ( rule__GeoJSON__UnorderedGroup_1__5 )?
            int alt35=2;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5350:2: rule__GeoJSON__UnorderedGroup_1__5
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__5_in_rule__GeoJSON__UnorderedGroup_1__411184);
                    rule__GeoJSON__UnorderedGroup_1__5();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__4"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5357:1: rule__GeoJSON__UnorderedGroup_1__5 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__6 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5361:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__6 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5362:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__6 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__511209);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5363:2: ( rule__GeoJSON__UnorderedGroup_1__6 )?
            int alt36=2;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5363:2: rule__GeoJSON__UnorderedGroup_1__6
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__6_in_rule__GeoJSON__UnorderedGroup_1__511212);
                    rule__GeoJSON__UnorderedGroup_1__6();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__5"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5370:1: rule__GeoJSON__UnorderedGroup_1__6 : rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__7 )? ;
    public final void rule__GeoJSON__UnorderedGroup_1__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5374:1: ( rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__7 )? )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5375:2: rule__GeoJSON__UnorderedGroup_1__Impl ( rule__GeoJSON__UnorderedGroup_1__7 )?
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__611237);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5376:2: ( rule__GeoJSON__UnorderedGroup_1__7 )?
            int alt37=2;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5376:2: rule__GeoJSON__UnorderedGroup_1__7
                    {
                    pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__7_in_rule__GeoJSON__UnorderedGroup_1__611240);
                    rule__GeoJSON__UnorderedGroup_1__7();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__6"


    // $ANTLR start "rule__GeoJSON__UnorderedGroup_1__7"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5383:1: rule__GeoJSON__UnorderedGroup_1__7 : rule__GeoJSON__UnorderedGroup_1__Impl ;
    public final void rule__GeoJSON__UnorderedGroup_1__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5387:1: ( rule__GeoJSON__UnorderedGroup_1__Impl )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5388:2: rule__GeoJSON__UnorderedGroup_1__Impl
            {
            pushFollow(FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__711265);
            rule__GeoJSON__UnorderedGroup_1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__UnorderedGroup_1__7"


    // $ANTLR start "rule__GeoJSON__TypeAssignment_1_0_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5411:1: rule__GeoJSON__TypeAssignment_1_0_2 : ( ruleType ) ;
    public final void rule__GeoJSON__TypeAssignment_1_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5415:1: ( ( ruleType ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5416:1: ( ruleType )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5416:1: ( ruleType )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5417:1: ruleType
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getTypeTypeEnumRuleCall_1_0_2_0()); 
            }
            pushFollow(FOLLOW_ruleType_in_rule__GeoJSON__TypeAssignment_1_0_211309);
            ruleType();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getTypeTypeEnumRuleCall_1_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__TypeAssignment_1_0_2"


    // $ANTLR start "rule__GeoJSON__CrsAssignment_1_1_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5426:1: rule__GeoJSON__CrsAssignment_1_1_2 : ( ruleCRS ) ;
    public final void rule__GeoJSON__CrsAssignment_1_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5430:1: ( ( ruleCRS ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5431:1: ( ruleCRS )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5431:1: ( ruleCRS )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5432:1: ruleCRS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getCrsCRSParserRuleCall_1_1_2_0()); 
            }
            pushFollow(FOLLOW_ruleCRS_in_rule__GeoJSON__CrsAssignment_1_1_211340);
            ruleCRS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getCrsCRSParserRuleCall_1_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__CrsAssignment_1_1_2"


    // $ANTLR start "rule__GeoJSON__BboxAssignment_1_2_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5441:1: rule__GeoJSON__BboxAssignment_1_2_2 : ( ruleCoordList ) ;
    public final void rule__GeoJSON__BboxAssignment_1_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5445:1: ( ( ruleCoordList ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5446:1: ( ruleCoordList )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5446:1: ( ruleCoordList )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5447:1: ruleCoordList
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getBboxCoordListParserRuleCall_1_2_2_0()); 
            }
            pushFollow(FOLLOW_ruleCoordList_in_rule__GeoJSON__BboxAssignment_1_2_211371);
            ruleCoordList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getBboxCoordListParserRuleCall_1_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__BboxAssignment_1_2_2"


    // $ANTLR start "rule__GeoJSON__GeometryAssignment_1_3_0_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5456:1: rule__GeoJSON__GeometryAssignment_1_3_0_2 : ( ruleGeometry ) ;
    public final void rule__GeoJSON__GeometryAssignment_1_3_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5460:1: ( ( ruleGeometry ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5461:1: ( ruleGeometry )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5461:1: ( ruleGeometry )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5462:1: ruleGeometry
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_0_2_0()); 
            }
            pushFollow(FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_0_211402);
            ruleGeometry();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__GeometryAssignment_1_3_0_2"


    // $ANTLR start "rule__GeoJSON__GeometryAssignment_1_3_1_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5471:1: rule__GeoJSON__GeometryAssignment_1_3_1_2 : ( ruleGeometry ) ;
    public final void rule__GeoJSON__GeometryAssignment_1_3_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5475:1: ( ( ruleGeometry ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5476:1: ( ruleGeometry )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5476:1: ( ruleGeometry )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5477:1: ruleGeometry
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_1_2_0()); 
            }
            pushFollow(FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_1_211433);
            ruleGeometry();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__GeometryAssignment_1_3_1_2"


    // $ANTLR start "rule__GeoJSON__GeometryAssignment_1_3_1_3_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5486:1: rule__GeoJSON__GeometryAssignment_1_3_1_3_1 : ( ruleGeometry ) ;
    public final void rule__GeoJSON__GeometryAssignment_1_3_1_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5490:1: ( ( ruleGeometry ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5491:1: ( ruleGeometry )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5491:1: ( ruleGeometry )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5492:1: ruleGeometry
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_1_3_1_0()); 
            }
            pushFollow(FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_1_3_111464);
            ruleGeometry();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getGeometryGeometryParserRuleCall_1_3_1_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__GeometryAssignment_1_3_1_3_1"


    // $ANTLR start "rule__GeoJSON__FlatPropertiesAssignment_1_4_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5501:1: rule__GeoJSON__FlatPropertiesAssignment_1_4_0 : ( ruleProperty ) ;
    public final void rule__GeoJSON__FlatPropertiesAssignment_1_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5505:1: ( ( ruleProperty ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5506:1: ( ruleProperty )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5506:1: ( ruleProperty )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5507:1: ruleProperty
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFlatPropertiesPropertyParserRuleCall_1_4_0_0()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_rule__GeoJSON__FlatPropertiesAssignment_1_4_011495);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFlatPropertiesPropertyParserRuleCall_1_4_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__FlatPropertiesAssignment_1_4_0"


    // $ANTLR start "rule__GeoJSON__PropertiesAssignment_1_5_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5516:1: rule__GeoJSON__PropertiesAssignment_1_5_2 : ( ruleStruct ) ;
    public final void rule__GeoJSON__PropertiesAssignment_1_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5520:1: ( ( ruleStruct ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5521:1: ( ruleStruct )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5521:1: ( ruleStruct )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5522:1: ruleStruct
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getPropertiesStructParserRuleCall_1_5_2_0()); 
            }
            pushFollow(FOLLOW_ruleStruct_in_rule__GeoJSON__PropertiesAssignment_1_5_211526);
            ruleStruct();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getPropertiesStructParserRuleCall_1_5_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__PropertiesAssignment_1_5_2"


    // $ANTLR start "rule__GeoJSON__StyleAssignment_1_6_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5531:1: rule__GeoJSON__StyleAssignment_1_6_2 : ( ruleStyle ) ;
    public final void rule__GeoJSON__StyleAssignment_1_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5535:1: ( ( ruleStyle ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5536:1: ( ruleStyle )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5536:1: ( ruleStyle )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5537:1: ruleStyle
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getStyleStyleParserRuleCall_1_6_2_0()); 
            }
            pushFollow(FOLLOW_ruleStyle_in_rule__GeoJSON__StyleAssignment_1_6_211557);
            ruleStyle();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getStyleStyleParserRuleCall_1_6_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__StyleAssignment_1_6_2"


    // $ANTLR start "rule__GeoJSON__FeaturesAssignment_1_7_3_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5546:1: rule__GeoJSON__FeaturesAssignment_1_7_3_0 : ( ruleFeature ) ;
    public final void rule__GeoJSON__FeaturesAssignment_1_7_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5550:1: ( ( ruleFeature ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5551:1: ( ruleFeature )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5551:1: ( ruleFeature )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5552:1: ruleFeature
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFeaturesFeatureParserRuleCall_1_7_3_0_0()); 
            }
            pushFollow(FOLLOW_ruleFeature_in_rule__GeoJSON__FeaturesAssignment_1_7_3_011588);
            ruleFeature();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFeaturesFeatureParserRuleCall_1_7_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__FeaturesAssignment_1_7_3_0"


    // $ANTLR start "rule__GeoJSON__FeaturesAssignment_1_7_3_1_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5561:1: rule__GeoJSON__FeaturesAssignment_1_7_3_1_1 : ( ruleFeature ) ;
    public final void rule__GeoJSON__FeaturesAssignment_1_7_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5565:1: ( ( ruleFeature ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5566:1: ( ruleFeature )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5566:1: ( ruleFeature )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5567:1: ruleFeature
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeoJSONAccess().getFeaturesFeatureParserRuleCall_1_7_3_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleFeature_in_rule__GeoJSON__FeaturesAssignment_1_7_3_1_111619);
            ruleFeature();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeoJSONAccess().getFeaturesFeatureParserRuleCall_1_7_3_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GeoJSON__FeaturesAssignment_1_7_3_1_1"


    // $ANTLR start "rule__CRS__TypeAssignment_3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5576:1: rule__CRS__TypeAssignment_3 : ( ruleCRSType ) ;
    public final void rule__CRS__TypeAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5580:1: ( ( ruleCRSType ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5581:1: ( ruleCRSType )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5581:1: ( ruleCRSType )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5582:1: ruleCRSType
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getTypeCRSTypeEnumRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_ruleCRSType_in_rule__CRS__TypeAssignment_311650);
            ruleCRSType();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getTypeCRSTypeEnumRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__TypeAssignment_3"


    // $ANTLR start "rule__CRS__NameAssignment_8_0_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5591:1: rule__CRS__NameAssignment_8_0_2 : ( RULE_STRING ) ;
    public final void rule__CRS__NameAssignment_8_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5595:1: ( ( RULE_STRING ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5596:1: ( RULE_STRING )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5596:1: ( RULE_STRING )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5597:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getNameSTRINGTerminalRuleCall_8_0_2_0()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__CRS__NameAssignment_8_0_211681); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getNameSTRINGTerminalRuleCall_8_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__NameAssignment_8_0_2"


    // $ANTLR start "rule__CRS__LinkCRSTypeAssignment_8_1_6"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5606:1: rule__CRS__LinkCRSTypeAssignment_8_1_6 : ( ruleLinkCRSType ) ;
    public final void rule__CRS__LinkCRSTypeAssignment_8_1_6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5610:1: ( ( ruleLinkCRSType ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5611:1: ( ruleLinkCRSType )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5611:1: ( ruleLinkCRSType )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5612:1: ruleLinkCRSType
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCRSAccess().getLinkCRSTypeLinkCRSTypeEnumRuleCall_8_1_6_0()); 
            }
            pushFollow(FOLLOW_ruleLinkCRSType_in_rule__CRS__LinkCRSTypeAssignment_8_1_611712);
            ruleLinkCRSType();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCRSAccess().getLinkCRSTypeLinkCRSTypeEnumRuleCall_8_1_6_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CRS__LinkCRSTypeAssignment_8_1_6"


    // $ANTLR start "rule__CoordList__CoordsAssignment_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5621:1: rule__CoordList__CoordsAssignment_1 : ( ruleDOUBLE ) ;
    public final void rule__CoordList__CoordsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5625:1: ( ( ruleDOUBLE ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5626:1: ( ruleDOUBLE )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5626:1: ( ruleDOUBLE )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5627:1: ruleDOUBLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getCoordsDOUBLEParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleDOUBLE_in_rule__CoordList__CoordsAssignment_111743);
            ruleDOUBLE();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getCoordsDOUBLEParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__CoordsAssignment_1"


    // $ANTLR start "rule__CoordList__CoordsAssignment_2_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5636:1: rule__CoordList__CoordsAssignment_2_1 : ( ruleDOUBLE ) ;
    public final void rule__CoordList__CoordsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5640:1: ( ( ruleDOUBLE ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5641:1: ( ruleDOUBLE )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5641:1: ( ruleDOUBLE )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5642:1: ruleDOUBLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordListAccess().getCoordsDOUBLEParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_ruleDOUBLE_in_rule__CoordList__CoordsAssignment_2_111774);
            ruleDOUBLE();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordListAccess().getCoordsDOUBLEParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordList__CoordsAssignment_2_1"


    // $ANTLR start "rule__Geometry__TypeAssignment_4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5651:1: rule__Geometry__TypeAssignment_4 : ( ruleGeometryType ) ;
    public final void rule__Geometry__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5655:1: ( ( ruleGeometryType ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5656:1: ( ruleGeometryType )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5656:1: ( ruleGeometryType )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5657:1: ruleGeometryType
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getTypeGeometryTypeEnumRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_ruleGeometryType_in_rule__Geometry__TypeAssignment_411805);
            ruleGeometryType();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getTypeGeometryTypeEnumRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__TypeAssignment_4"


    // $ANTLR start "rule__Geometry__CoordsAssignment_9"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5666:1: rule__Geometry__CoordsAssignment_9 : ( ruleCoord ) ;
    public final void rule__Geometry__CoordsAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5670:1: ( ( ruleCoord ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5671:1: ( ruleCoord )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5671:1: ( ruleCoord )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5672:1: ruleCoord
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGeometryAccess().getCoordsCoordParserRuleCall_9_0()); 
            }
            pushFollow(FOLLOW_ruleCoord_in_rule__Geometry__CoordsAssignment_911836);
            ruleCoord();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGeometryAccess().getCoordsCoordParserRuleCall_9_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Geometry__CoordsAssignment_9"


    // $ANTLR start "rule__CoordArray__CoordsAssignment_2_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5681:1: rule__CoordArray__CoordsAssignment_2_0 : ( ruleCoord ) ;
    public final void rule__CoordArray__CoordsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5685:1: ( ( ruleCoord ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5686:1: ( ruleCoord )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5686:1: ( ruleCoord )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5687:1: ruleCoord
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCoordsCoordParserRuleCall_2_0_0()); 
            }
            pushFollow(FOLLOW_ruleCoord_in_rule__CoordArray__CoordsAssignment_2_011867);
            ruleCoord();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCoordsCoordParserRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__CoordsAssignment_2_0"


    // $ANTLR start "rule__CoordArray__CoordsAssignment_2_1_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5696:1: rule__CoordArray__CoordsAssignment_2_1_1 : ( ruleCoord ) ;
    public final void rule__CoordArray__CoordsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5700:1: ( ( ruleCoord ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5701:1: ( ruleCoord )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5701:1: ( ruleCoord )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5702:1: ruleCoord
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCoordArrayAccess().getCoordsCoordParserRuleCall_2_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleCoord_in_rule__CoordArray__CoordsAssignment_2_1_111898);
            ruleCoord();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCoordArrayAccess().getCoordsCoordParserRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CoordArray__CoordsAssignment_2_1_1"


    // $ANTLR start "rule__Style__PropertyAssignment_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5711:1: rule__Style__PropertyAssignment_1 : ( ruleProperty ) ;
    public final void rule__Style__PropertyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5715:1: ( ( ruleProperty ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5716:1: ( ruleProperty )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5716:1: ( ruleProperty )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5717:1: ruleProperty
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getPropertyPropertyParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_rule__Style__PropertyAssignment_111929);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getPropertyPropertyParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__PropertyAssignment_1"


    // $ANTLR start "rule__Style__PropertyAssignment_2_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5726:1: rule__Style__PropertyAssignment_2_1 : ( ruleProperty ) ;
    public final void rule__Style__PropertyAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5730:1: ( ( ruleProperty ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5731:1: ( ruleProperty )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5731:1: ( ruleProperty )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5732:1: ruleProperty
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStyleAccess().getPropertyPropertyParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_rule__Style__PropertyAssignment_2_111960);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStyleAccess().getPropertyPropertyParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Style__PropertyAssignment_2_1"


    // $ANTLR start "rule__Feature__GeometryAssignment_3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5741:1: rule__Feature__GeometryAssignment_3 : ( ruleGeometry ) ;
    public final void rule__Feature__GeometryAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5745:1: ( ( ruleGeometry ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5746:1: ( ruleGeometry )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5746:1: ( ruleGeometry )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5747:1: ruleGeometry
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getGeometryGeometryParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_ruleGeometry_in_rule__Feature__GeometryAssignment_311991);
            ruleGeometry();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getGeometryGeometryParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__GeometryAssignment_3"


    // $ANTLR start "rule__Feature__PropertiesAssignment_7"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5756:1: rule__Feature__PropertiesAssignment_7 : ( ruleStruct ) ;
    public final void rule__Feature__PropertiesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5760:1: ( ( ruleStruct ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5761:1: ( ruleStruct )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5761:1: ( ruleStruct )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5762:1: ruleStruct
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getPropertiesStructParserRuleCall_7_0()); 
            }
            pushFollow(FOLLOW_ruleStruct_in_rule__Feature__PropertiesAssignment_712022);
            ruleStruct();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getPropertiesStructParserRuleCall_7_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Feature__PropertiesAssignment_7"


    // $ANTLR start "rule__Struct__PropertyAssignment_2_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5771:1: rule__Struct__PropertyAssignment_2_0 : ( ruleProperty ) ;
    public final void rule__Struct__PropertyAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5775:1: ( ( ruleProperty ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5776:1: ( ruleProperty )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5776:1: ( ruleProperty )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5777:1: ruleProperty
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getPropertyPropertyParserRuleCall_2_0_0()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_rule__Struct__PropertyAssignment_2_012053);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getPropertyPropertyParserRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__PropertyAssignment_2_0"


    // $ANTLR start "rule__Struct__PropertyAssignment_2_1_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5786:1: rule__Struct__PropertyAssignment_2_1_1 : ( ruleProperty ) ;
    public final void rule__Struct__PropertyAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5790:1: ( ( ruleProperty ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5791:1: ( ruleProperty )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5791:1: ( ruleProperty )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5792:1: ruleProperty
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStructAccess().getPropertyPropertyParserRuleCall_2_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleProperty_in_rule__Struct__PropertyAssignment_2_1_112084);
            ruleProperty();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStructAccess().getPropertyPropertyParserRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Struct__PropertyAssignment_2_1_1"


    // $ANTLR start "rule__Property__NameAssignment_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5801:1: rule__Property__NameAssignment_0 : ( RULE_STRING ) ;
    public final void rule__Property__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5805:1: ( ( RULE_STRING ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5806:1: ( RULE_STRING )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5806:1: ( RULE_STRING )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5807:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getNameSTRINGTerminalRuleCall_0_0()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Property__NameAssignment_012115); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getNameSTRINGTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__NameAssignment_0"


    // $ANTLR start "rule__Property__ValueAssignment_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5816:1: rule__Property__ValueAssignment_2 : ( ruleValue ) ;
    public final void rule__Property__ValueAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5820:1: ( ( ruleValue ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5821:1: ( ruleValue )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5821:1: ( ruleValue )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5822:1: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPropertyAccess().getValueValueParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleValue_in_rule__Property__ValueAssignment_212146);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPropertyAccess().getValueValueParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__ValueAssignment_2"


    // $ANTLR start "rule__Value__IntValueAssignment_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5831:1: rule__Value__IntValueAssignment_0 : ( RULE_INT ) ;
    public final void rule__Value__IntValueAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5835:1: ( ( RULE_INT ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5836:1: ( RULE_INT )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5836:1: ( RULE_INT )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5837:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getIntValueINTTerminalRuleCall_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Value__IntValueAssignment_012177); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getIntValueINTTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__IntValueAssignment_0"


    // $ANTLR start "rule__Value__NintValueAssignment_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5846:1: rule__Value__NintValueAssignment_1 : ( ruleNINT ) ;
    public final void rule__Value__NintValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5850:1: ( ( ruleNINT ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5851:1: ( ruleNINT )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5851:1: ( ruleNINT )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5852:1: ruleNINT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getNintValueNINTParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleNINT_in_rule__Value__NintValueAssignment_112208);
            ruleNINT();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getNintValueNINTParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__NintValueAssignment_1"


    // $ANTLR start "rule__Value__DoubleValueAssignment_2"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5861:1: rule__Value__DoubleValueAssignment_2 : ( ruleDOUBLE ) ;
    public final void rule__Value__DoubleValueAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5865:1: ( ( ruleDOUBLE ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5866:1: ( ruleDOUBLE )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5866:1: ( ruleDOUBLE )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5867:1: ruleDOUBLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getDoubleValueDOUBLEParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleDOUBLE_in_rule__Value__DoubleValueAssignment_212239);
            ruleDOUBLE();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getDoubleValueDOUBLEParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__DoubleValueAssignment_2"


    // $ANTLR start "rule__Value__StringValueAssignment_3"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5876:1: rule__Value__StringValueAssignment_3 : ( RULE_STRING ) ;
    public final void rule__Value__StringValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5880:1: ( ( RULE_STRING ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5881:1: ( RULE_STRING )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5881:1: ( RULE_STRING )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5882:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getStringValueSTRINGTerminalRuleCall_3_0()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Value__StringValueAssignment_312270); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getStringValueSTRINGTerminalRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__StringValueAssignment_3"


    // $ANTLR start "rule__Value__ArrayValueAssignment_4"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5891:1: rule__Value__ArrayValueAssignment_4 : ( ruleArray ) ;
    public final void rule__Value__ArrayValueAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5895:1: ( ( ruleArray ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5896:1: ( ruleArray )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5896:1: ( ruleArray )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5897:1: ruleArray
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getArrayValueArrayParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_ruleArray_in_rule__Value__ArrayValueAssignment_412301);
            ruleArray();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getArrayValueArrayParserRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ArrayValueAssignment_4"


    // $ANTLR start "rule__Value__StructValueAssignment_5"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5906:1: rule__Value__StructValueAssignment_5 : ( ruleStruct ) ;
    public final void rule__Value__StructValueAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5910:1: ( ( ruleStruct ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5911:1: ( ruleStruct )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5911:1: ( ruleStruct )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5912:1: ruleStruct
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getStructValueStructParserRuleCall_5_0()); 
            }
            pushFollow(FOLLOW_ruleStruct_in_rule__Value__StructValueAssignment_512332);
            ruleStruct();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getStructValueStructParserRuleCall_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__StructValueAssignment_5"


    // $ANTLR start "rule__Array__ValuesAssignment_2_0"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5921:1: rule__Array__ValuesAssignment_2_0 : ( ruleValue ) ;
    public final void rule__Array__ValuesAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5925:1: ( ( ruleValue ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5926:1: ( ruleValue )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5926:1: ( ruleValue )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5927:1: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getValuesValueParserRuleCall_2_0_0()); 
            }
            pushFollow(FOLLOW_ruleValue_in_rule__Array__ValuesAssignment_2_012363);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getValuesValueParserRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__ValuesAssignment_2_0"


    // $ANTLR start "rule__Array__ValuesAssignment_2_1_1"
    // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5936:1: rule__Array__ValuesAssignment_2_1_1 : ( ruleValue ) ;
    public final void rule__Array__ValuesAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5940:1: ( ( ruleValue ) )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5941:1: ( ruleValue )
            {
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5941:1: ( ruleValue )
            // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5942:1: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayAccess().getValuesValueParserRuleCall_2_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleValue_in_rule__Array__ValuesAssignment_2_1_112394);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayAccess().getValuesValueParserRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Array__ValuesAssignment_2_1_1"

    // $ANTLR start synpred1_InternalGeoJSON
    public final void synpred1_InternalGeoJSON_fragment() throws RecognitionException {   
        // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5226:8: ( rule__GeoJSON__Group_1_4__0 )
        // ../io.mapzone.geojson.ui/src-gen/io/mapzone/ui/contentassist/antlr/internal/InternalGeoJSON.g:5226:9: rule__GeoJSON__Group_1_4__0
        {
        pushFollow(FOLLOW_rule__GeoJSON__Group_1_4__0_in_synpred1_InternalGeoJSON10727);
        rule__GeoJSON__Group_1_4__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalGeoJSON

    // Delegated rules

    public final boolean synpred1_InternalGeoJSON() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalGeoJSON_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA29 dfa29 = new DFA29(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA33 dfa33 = new DFA33(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA37 dfa37 = new DFA37(this);
    static final String DFA4_eotS =
        "\12\uffff";
    static final String DFA4_eofS =
        "\1\uffff\1\7\6\uffff\1\11\1\uffff";
    static final String DFA4_minS =
        "\1\4\1\5\1\4\5\uffff\1\5\1\uffff";
    static final String DFA4_maxS =
        "\1\51\1\52\1\4\5\uffff\1\52\1\uffff";
    static final String DFA4_acceptS =
        "\3\uffff\1\4\1\5\1\6\1\3\1\1\1\uffff\1\2";
    static final String DFA4_specialS =
        "\12\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1\1\3\23\uffff\1\5\13\uffff\1\4\3\uffff\1\2",
            "\1\7\24\uffff\2\7\1\uffff\10\7\1\uffff\1\7\3\uffff\1\6",
            "\1\10",
            "",
            "",
            "",
            "",
            "",
            "\1\11\24\uffff\2\11\1\uffff\10\11\1\uffff\1\11\3\uffff\1\6",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "594:1: rule__Value__Alternatives : ( ( ( rule__Value__IntValueAssignment_0 ) ) | ( ( rule__Value__NintValueAssignment_1 ) ) | ( ( rule__Value__DoubleValueAssignment_2 ) ) | ( ( rule__Value__StringValueAssignment_3 ) ) | ( ( rule__Value__ArrayValueAssignment_4 ) ) | ( ( rule__Value__StructValueAssignment_5 ) ) );";
        }
    }
    static final String DFA29_eotS =
        "\14\uffff";
    static final String DFA29_eofS =
        "\14\uffff";
    static final String DFA29_minS =
        "\1\5\5\uffff\1\0\5\uffff";
    static final String DFA29_maxS =
        "\1\44\5\uffff\1\0\5\uffff";
    static final String DFA29_acceptS =
        "\1\uffff\1\2\11\uffff\1\1";
    static final String DFA29_specialS =
        "\6\uffff\1\0\5\uffff}>";
    static final String[] DFA29_transitionS = {
            "\1\6\24\uffff\2\1\2\uffff\7\1",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA29_eot = DFA.unpackEncodedString(DFA29_eotS);
    static final short[] DFA29_eof = DFA.unpackEncodedString(DFA29_eofS);
    static final char[] DFA29_min = DFA.unpackEncodedStringToUnsignedChars(DFA29_minS);
    static final char[] DFA29_max = DFA.unpackEncodedStringToUnsignedChars(DFA29_maxS);
    static final short[] DFA29_accept = DFA.unpackEncodedString(DFA29_acceptS);
    static final short[] DFA29_special = DFA.unpackEncodedString(DFA29_specialS);
    static final short[][] DFA29_transition;

    static {
        int numStates = DFA29_transitionS.length;
        DFA29_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA29_transition[i] = DFA.unpackEncodedString(DFA29_transitionS[i]);
        }
    }

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = DFA29_eot;
            this.eof = DFA29_eof;
            this.min = DFA29_min;
            this.max = DFA29_max;
            this.accept = DFA29_accept;
            this.special = DFA29_special;
            this.transition = DFA29_transition;
        }
        public String getDescription() {
            return "()* loopback of 5226:7: ( ( rule__GeoJSON__Group_1_4__0 )=> rule__GeoJSON__Group_1_4__0 )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA29_6 = input.LA(1);

                         
                        int index29_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGeoJSON()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index29_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 29, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA31_eotS =
        "\12\uffff";
    static final String DFA31_eofS =
        "\12\uffff";
    static final String DFA31_minS =
        "\1\5\11\uffff";
    static final String DFA31_maxS =
        "\1\44\11\uffff";
    static final String DFA31_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA31_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA31_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "5298:2: ( rule__GeoJSON__UnorderedGroup_1__1 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_0 = input.LA(1);

                         
                        int index31_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA31_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA31_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA31_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA31_0 >=32 && LA31_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA31_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA31_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA31_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA31_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA31_0==26) ) {s = 9;}

                         
                        input.seek(index31_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 31, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA32_eotS =
        "\12\uffff";
    static final String DFA32_eofS =
        "\12\uffff";
    static final String DFA32_minS =
        "\1\5\11\uffff";
    static final String DFA32_maxS =
        "\1\44\11\uffff";
    static final String DFA32_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA32_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA32_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "5311:2: ( rule__GeoJSON__UnorderedGroup_1__2 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA32_0 = input.LA(1);

                         
                        int index32_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA32_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA32_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA32_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA32_0 >=32 && LA32_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA32_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA32_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA32_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA32_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA32_0==26) ) {s = 9;}

                         
                        input.seek(index32_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 32, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA33_eotS =
        "\12\uffff";
    static final String DFA33_eofS =
        "\12\uffff";
    static final String DFA33_minS =
        "\1\5\11\uffff";
    static final String DFA33_maxS =
        "\1\44\11\uffff";
    static final String DFA33_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA33_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA33_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA33_eot = DFA.unpackEncodedString(DFA33_eotS);
    static final short[] DFA33_eof = DFA.unpackEncodedString(DFA33_eofS);
    static final char[] DFA33_min = DFA.unpackEncodedStringToUnsignedChars(DFA33_minS);
    static final char[] DFA33_max = DFA.unpackEncodedStringToUnsignedChars(DFA33_maxS);
    static final short[] DFA33_accept = DFA.unpackEncodedString(DFA33_acceptS);
    static final short[] DFA33_special = DFA.unpackEncodedString(DFA33_specialS);
    static final short[][] DFA33_transition;

    static {
        int numStates = DFA33_transitionS.length;
        DFA33_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA33_transition[i] = DFA.unpackEncodedString(DFA33_transitionS[i]);
        }
    }

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = DFA33_eot;
            this.eof = DFA33_eof;
            this.min = DFA33_min;
            this.max = DFA33_max;
            this.accept = DFA33_accept;
            this.special = DFA33_special;
            this.transition = DFA33_transition;
        }
        public String getDescription() {
            return "5324:2: ( rule__GeoJSON__UnorderedGroup_1__3 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA33_0 = input.LA(1);

                         
                        int index33_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA33_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA33_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA33_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA33_0 >=32 && LA33_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA33_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA33_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA33_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA33_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA33_0==26) ) {s = 9;}

                         
                        input.seek(index33_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 33, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA34_eotS =
        "\12\uffff";
    static final String DFA34_eofS =
        "\12\uffff";
    static final String DFA34_minS =
        "\1\5\11\uffff";
    static final String DFA34_maxS =
        "\1\44\11\uffff";
    static final String DFA34_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA34_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA34_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA34_eot = DFA.unpackEncodedString(DFA34_eotS);
    static final short[] DFA34_eof = DFA.unpackEncodedString(DFA34_eofS);
    static final char[] DFA34_min = DFA.unpackEncodedStringToUnsignedChars(DFA34_minS);
    static final char[] DFA34_max = DFA.unpackEncodedStringToUnsignedChars(DFA34_maxS);
    static final short[] DFA34_accept = DFA.unpackEncodedString(DFA34_acceptS);
    static final short[] DFA34_special = DFA.unpackEncodedString(DFA34_specialS);
    static final short[][] DFA34_transition;

    static {
        int numStates = DFA34_transitionS.length;
        DFA34_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA34_transition[i] = DFA.unpackEncodedString(DFA34_transitionS[i]);
        }
    }

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = DFA34_eot;
            this.eof = DFA34_eof;
            this.min = DFA34_min;
            this.max = DFA34_max;
            this.accept = DFA34_accept;
            this.special = DFA34_special;
            this.transition = DFA34_transition;
        }
        public String getDescription() {
            return "5337:2: ( rule__GeoJSON__UnorderedGroup_1__4 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_0 = input.LA(1);

                         
                        int index34_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA34_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA34_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA34_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA34_0 >=32 && LA34_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA34_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA34_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA34_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA34_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA34_0==26) ) {s = 9;}

                         
                        input.seek(index34_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 34, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA35_eotS =
        "\12\uffff";
    static final String DFA35_eofS =
        "\12\uffff";
    static final String DFA35_minS =
        "\1\5\11\uffff";
    static final String DFA35_maxS =
        "\1\44\11\uffff";
    static final String DFA35_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA35_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA35_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA35_eot = DFA.unpackEncodedString(DFA35_eotS);
    static final short[] DFA35_eof = DFA.unpackEncodedString(DFA35_eofS);
    static final char[] DFA35_min = DFA.unpackEncodedStringToUnsignedChars(DFA35_minS);
    static final char[] DFA35_max = DFA.unpackEncodedStringToUnsignedChars(DFA35_maxS);
    static final short[] DFA35_accept = DFA.unpackEncodedString(DFA35_acceptS);
    static final short[] DFA35_special = DFA.unpackEncodedString(DFA35_specialS);
    static final short[][] DFA35_transition;

    static {
        int numStates = DFA35_transitionS.length;
        DFA35_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
        }
    }

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = DFA35_eot;
            this.eof = DFA35_eof;
            this.min = DFA35_min;
            this.max = DFA35_max;
            this.accept = DFA35_accept;
            this.special = DFA35_special;
            this.transition = DFA35_transition;
        }
        public String getDescription() {
            return "5350:2: ( rule__GeoJSON__UnorderedGroup_1__5 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA35_0 = input.LA(1);

                         
                        int index35_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA35_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA35_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA35_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA35_0 >=32 && LA35_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA35_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA35_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA35_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA35_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA35_0==26) ) {s = 9;}

                         
                        input.seek(index35_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 35, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA36_eotS =
        "\12\uffff";
    static final String DFA36_eofS =
        "\12\uffff";
    static final String DFA36_minS =
        "\1\5\11\uffff";
    static final String DFA36_maxS =
        "\1\44\11\uffff";
    static final String DFA36_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA36_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA36_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA36_eot = DFA.unpackEncodedString(DFA36_eotS);
    static final short[] DFA36_eof = DFA.unpackEncodedString(DFA36_eofS);
    static final char[] DFA36_min = DFA.unpackEncodedStringToUnsignedChars(DFA36_minS);
    static final char[] DFA36_max = DFA.unpackEncodedStringToUnsignedChars(DFA36_maxS);
    static final short[] DFA36_accept = DFA.unpackEncodedString(DFA36_acceptS);
    static final short[] DFA36_special = DFA.unpackEncodedString(DFA36_specialS);
    static final short[][] DFA36_transition;

    static {
        int numStates = DFA36_transitionS.length;
        DFA36_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA36_transition[i] = DFA.unpackEncodedString(DFA36_transitionS[i]);
        }
    }

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = DFA36_eot;
            this.eof = DFA36_eof;
            this.min = DFA36_min;
            this.max = DFA36_max;
            this.accept = DFA36_accept;
            this.special = DFA36_special;
            this.transition = DFA36_transition;
        }
        public String getDescription() {
            return "5363:2: ( rule__GeoJSON__UnorderedGroup_1__6 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA36_0 = input.LA(1);

                         
                        int index36_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA36_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA36_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA36_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA36_0 >=32 && LA36_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA36_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA36_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA36_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA36_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA36_0==26) ) {s = 9;}

                         
                        input.seek(index36_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA37_eotS =
        "\12\uffff";
    static final String DFA37_eofS =
        "\12\uffff";
    static final String DFA37_minS =
        "\1\5\11\uffff";
    static final String DFA37_maxS =
        "\1\44\11\uffff";
    static final String DFA37_acceptS =
        "\1\uffff\10\1\1\2";
    static final String DFA37_specialS =
        "\1\0\11\uffff}>";
    static final String[] DFA37_transitionS = {
            "\1\5\24\uffff\1\11\1\1\2\uffff\1\2\1\3\2\4\1\6\1\7\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA37_eot = DFA.unpackEncodedString(DFA37_eotS);
    static final short[] DFA37_eof = DFA.unpackEncodedString(DFA37_eofS);
    static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars(DFA37_minS);
    static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars(DFA37_maxS);
    static final short[] DFA37_accept = DFA.unpackEncodedString(DFA37_acceptS);
    static final short[] DFA37_special = DFA.unpackEncodedString(DFA37_specialS);
    static final short[][] DFA37_transition;

    static {
        int numStates = DFA37_transitionS.length;
        DFA37_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA37_transition[i] = DFA.unpackEncodedString(DFA37_transitionS[i]);
        }
    }

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = DFA37_eot;
            this.eof = DFA37_eof;
            this.min = DFA37_min;
            this.max = DFA37_max;
            this.accept = DFA37_accept;
            this.special = DFA37_special;
            this.transition = DFA37_transition;
        }
        public String getDescription() {
            return "5376:2: ( rule__GeoJSON__UnorderedGroup_1__7 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_0 = input.LA(1);

                         
                        int index37_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA37_0 ==27 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 0) ) {s = 1;}

                        else if ( LA37_0 ==30 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 1) ) {s = 2;}

                        else if ( LA37_0 ==31 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 2) ) {s = 3;}

                        else if ( LA37_0 >=32 && LA37_0<=33 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 3) ) {s = 4;}

                        else if ( LA37_0 ==RULE_STRING && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 4) ) {s = 5;}

                        else if ( LA37_0 ==34 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 5) ) {s = 6;}

                        else if ( LA37_0 ==35 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 6) ) {s = 7;}

                        else if ( LA37_0 ==36 && getUnorderedGroupHelper().canSelect(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), 7) ) {s = 8;}

                        else if ( (LA37_0==26) ) {s = 9;}

                         
                        input.seek(index37_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_ruleGeoJSON_in_entryRuleGeoJSON61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGeoJSON68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__0_in_ruleGeoJSON94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCRS_in_entryRuleCRS121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCRS128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__0_in_ruleCRS154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoordList_in_entryRuleCoordList181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCoordList188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group__0_in_ruleCoordList214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometry_in_entryRuleGeometry241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGeometry248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__0_in_ruleGeometry274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoord_in_entryRuleCoord301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCoord308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Coord__Alternatives_in_ruleCoord334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoordArray_in_entryRuleCoordArray361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCoordArray368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__0_in_ruleCoordArray394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStyle_in_entryRuleStyle421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStyle428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group__0_in_ruleStyle454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeature_in_entryRuleFeature481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeature488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__0_in_ruleFeature514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStruct_in_entryRuleStruct541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStruct548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group__0_in_ruleStruct574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__0_in_ruleProperty634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_entryRuleValue661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValue668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__Alternatives_in_ruleValue694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArray_in_entryRuleArray721 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArray728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group__0_in_ruleArray754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNINT_in_entryRuleNINT781 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNINT788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__NINT__Group__0_in_ruleNINT814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDOUBLE_in_entryRuleDOUBLE841 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDOUBLE848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group__0_in_ruleDOUBLE874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Type__Alternatives_in_ruleType911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRSType__Alternatives_in_ruleCRSType947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LinkCRSType__Alternatives_in_ruleLinkCRSType983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeometryType__Alternatives_in_ruleGeometryType1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__0_in_rule__GeoJSON__Alternatives_1_31054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__0_in_rule__GeoJSON__Alternatives_1_31072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__0_in_rule__CRS__Alternatives_81105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__0_in_rule__CRS__Alternatives_81123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoordList_in_rule__Coord__Alternatives1156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoordArray_in_rule__Coord__Alternatives1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__IntValueAssignment_0_in_rule__Value__Alternatives1205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__NintValueAssignment_1_in_rule__Value__Alternatives1223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__DoubleValueAssignment_2_in_rule__Value__Alternatives1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__StringValueAssignment_3_in_rule__Value__Alternatives1259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__ArrayValueAssignment_4_in_rule__Value__Alternatives1277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Value__StructValueAssignment_5_in_rule__Value__Alternatives1295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__DOUBLE__Alternatives_01328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNINT_in_rule__DOUBLE__Alternatives_01345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Type__Alternatives1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Type__Alternatives1399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Type__Alternatives1420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Type__Alternatives1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Type__Alternatives1462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Type__Alternatives1483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__Type__Alternatives1504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Type__Alternatives1525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Type__Alternatives1546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__CRSType__Alternatives1582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__CRSType__Alternatives1603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__LinkCRSType__Alternatives1639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__LinkCRSType__Alternatives1660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__LinkCRSType__Alternatives1681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__GeometryType__Alternatives1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GeometryType__Alternatives1738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__GeometryType__Alternatives1759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__GeometryType__Alternatives1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__GeometryType__Alternatives1801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__GeometryType__Alternatives1822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__GeometryType__Alternatives1843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__0__Impl_in_rule__GeoJSON__Group__01876 = new BitSet(new long[]{0x0000001FC8000020L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__1_in_rule__GeoJSON__Group__01879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__GeoJSON__Group__0__Impl1907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__1__Impl_in_rule__GeoJSON__Group__11938 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__2_in_rule__GeoJSON__Group__11941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1_in_rule__GeoJSON__Group__1__Impl1968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group__2__Impl_in_rule__GeoJSON__Group__21998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__GeoJSON__Group__2__Impl2026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__0__Impl_in_rule__GeoJSON__Group_1_0__02063 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__1_in_rule__GeoJSON__Group_1_0__02066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__GeoJSON__Group_1_0__0__Impl2094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__1__Impl_in_rule__GeoJSON__Group_1_0__12125 = new BitSet(new long[]{0x00000000000FF800L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__2_in_rule__GeoJSON__Group_1_0__12128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_0__1__Impl2156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__2__Impl_in_rule__GeoJSON__Group_1_0__22187 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__3_in_rule__GeoJSON__Group_1_0__22190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__TypeAssignment_1_0_2_in_rule__GeoJSON__Group_1_0__2__Impl2217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__3__Impl_in_rule__GeoJSON__Group_1_0__32247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_0__3__Impl2276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__0__Impl_in_rule__GeoJSON__Group_1_1__02317 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__1_in_rule__GeoJSON__Group_1_1__02320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__GeoJSON__Group_1_1__0__Impl2348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__1__Impl_in_rule__GeoJSON__Group_1_1__12379 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__2_in_rule__GeoJSON__Group_1_1__12382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_1__1__Impl2410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__2__Impl_in_rule__GeoJSON__Group_1_1__22441 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__3_in_rule__GeoJSON__Group_1_1__22444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__CrsAssignment_1_1_2_in_rule__GeoJSON__Group_1_1__2__Impl2471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__3__Impl_in_rule__GeoJSON__Group_1_1__32501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_1__3__Impl2530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__0__Impl_in_rule__GeoJSON__Group_1_2__02571 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__1_in_rule__GeoJSON__Group_1_2__02574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__GeoJSON__Group_1_2__0__Impl2602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__1__Impl_in_rule__GeoJSON__Group_1_2__12633 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__2_in_rule__GeoJSON__Group_1_2__12636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_2__1__Impl2664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__2__Impl_in_rule__GeoJSON__Group_1_2__22695 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__3_in_rule__GeoJSON__Group_1_2__22698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__BboxAssignment_1_2_2_in_rule__GeoJSON__Group_1_2__2__Impl2725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__3__Impl_in_rule__GeoJSON__Group_1_2__32755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_2__3__Impl2784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__0__Impl_in_rule__GeoJSON__Group_1_3_0__02825 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__1_in_rule__GeoJSON__Group_1_3_0__02828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__GeoJSON__Group_1_3_0__0__Impl2856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__1__Impl_in_rule__GeoJSON__Group_1_3_0__12887 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__2_in_rule__GeoJSON__Group_1_3_0__12890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_3_0__1__Impl2918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__2__Impl_in_rule__GeoJSON__Group_1_3_0__22949 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__3_in_rule__GeoJSON__Group_1_3_0__22952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_0_2_in_rule__GeoJSON__Group_1_3_0__2__Impl2979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_0__3__Impl_in_rule__GeoJSON__Group_1_3_0__33009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_3_0__3__Impl3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__0__Impl_in_rule__GeoJSON__Group_1_3_1__03076 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__1_in_rule__GeoJSON__Group_1_3_1__03079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__GeoJSON__Group_1_3_1__0__Impl3107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__1__Impl_in_rule__GeoJSON__Group_1_3_1__13138 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__2_in_rule__GeoJSON__Group_1_3_1__13141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_3_1__1__Impl3169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__2__Impl_in_rule__GeoJSON__Group_1_3_1__23200 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__3_in_rule__GeoJSON__Group_1_3_1__23203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_1_2_in_rule__GeoJSON__Group_1_3_1__2__Impl3230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__3__Impl_in_rule__GeoJSON__Group_1_3_1__33260 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__4_in_rule__GeoJSON__Group_1_3_1__33263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1_3__0_in_rule__GeoJSON__Group_1_3_1__3__Impl3292 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1_3__0_in_rule__GeoJSON__Group_1_3_1__3__Impl3304 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1__4__Impl_in_rule__GeoJSON__Group_1_3_1__43337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_3_1__4__Impl3366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1_3__0__Impl_in_rule__GeoJSON__Group_1_3_1_3__03409 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1_3__1_in_rule__GeoJSON__Group_1_3_1_3__03412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_3_1_3__0__Impl3440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_3_1_3__1__Impl_in_rule__GeoJSON__Group_1_3_1_3__13471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__GeometryAssignment_1_3_1_3_1_in_rule__GeoJSON__Group_1_3_1_3__1__Impl3498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__0__Impl_in_rule__GeoJSON__Group_1_4__03532 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__1_in_rule__GeoJSON__Group_1_4__03535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__FlatPropertiesAssignment_1_4_0_in_rule__GeoJSON__Group_1_4__0__Impl3562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__1__Impl_in_rule__GeoJSON__Group_1_4__13592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_4__1__Impl3621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__0__Impl_in_rule__GeoJSON__Group_1_5__03658 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__1_in_rule__GeoJSON__Group_1_5__03661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__GeoJSON__Group_1_5__0__Impl3689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__1__Impl_in_rule__GeoJSON__Group_1_5__13720 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__2_in_rule__GeoJSON__Group_1_5__13723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_5__1__Impl3751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__2__Impl_in_rule__GeoJSON__Group_1_5__23782 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__3_in_rule__GeoJSON__Group_1_5__23785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__PropertiesAssignment_1_5_2_in_rule__GeoJSON__Group_1_5__2__Impl3812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__3__Impl_in_rule__GeoJSON__Group_1_5__33842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_5__3__Impl3871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__0__Impl_in_rule__GeoJSON__Group_1_6__03912 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__1_in_rule__GeoJSON__Group_1_6__03915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__GeoJSON__Group_1_6__0__Impl3943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__1__Impl_in_rule__GeoJSON__Group_1_6__13974 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__2_in_rule__GeoJSON__Group_1_6__13977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_6__1__Impl4005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__2__Impl_in_rule__GeoJSON__Group_1_6__24036 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__3_in_rule__GeoJSON__Group_1_6__24039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__StyleAssignment_1_6_2_in_rule__GeoJSON__Group_1_6__2__Impl4066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__3__Impl_in_rule__GeoJSON__Group_1_6__34096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_6__3__Impl4125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__0__Impl_in_rule__GeoJSON__Group_1_7__04166 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__1_in_rule__GeoJSON__Group_1_7__04169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__GeoJSON__Group_1_7__0__Impl4197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__1__Impl_in_rule__GeoJSON__Group_1_7__14228 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__2_in_rule__GeoJSON__Group_1_7__14231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__GeoJSON__Group_1_7__1__Impl4259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__2__Impl_in_rule__GeoJSON__Group_1_7__24290 = new BitSet(new long[]{0x0000004002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__3_in_rule__GeoJSON__Group_1_7__24293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__GeoJSON__Group_1_7__2__Impl4321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__3__Impl_in_rule__GeoJSON__Group_1_7__34352 = new BitSet(new long[]{0x0000004002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__4_in_rule__GeoJSON__Group_1_7__34355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3__0_in_rule__GeoJSON__Group_1_7__3__Impl4382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__4__Impl_in_rule__GeoJSON__Group_1_7__44413 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__5_in_rule__GeoJSON__Group_1_7__44416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__GeoJSON__Group_1_7__4__Impl4444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__5__Impl_in_rule__GeoJSON__Group_1_7__54475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_7__5__Impl4504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3__0__Impl_in_rule__GeoJSON__Group_1_7_3__04549 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3__1_in_rule__GeoJSON__Group_1_7_3__04552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__FeaturesAssignment_1_7_3_0_in_rule__GeoJSON__Group_1_7_3__0__Impl4579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3__1__Impl_in_rule__GeoJSON__Group_1_7_3__14609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3_1__0_in_rule__GeoJSON__Group_1_7_3__1__Impl4636 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3_1__0__Impl_in_rule__GeoJSON__Group_1_7_3_1__04671 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3_1__1_in_rule__GeoJSON__Group_1_7_3_1__04674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GeoJSON__Group_1_7_3_1__0__Impl4702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7_3_1__1__Impl_in_rule__GeoJSON__Group_1_7_3_1__14733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__FeaturesAssignment_1_7_3_1_1_in_rule__GeoJSON__Group_1_7_3_1__1__Impl4760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__0__Impl_in_rule__CRS__Group__04794 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__1_in_rule__CRS__Group__04797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__CRS__Group__0__Impl4825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__1__Impl_in_rule__CRS__Group__14856 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__2_in_rule__CRS__Group__14859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__CRS__Group__1__Impl4887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__2__Impl_in_rule__CRS__Group__24918 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_rule__CRS__Group__3_in_rule__CRS__Group__24921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__CRS__Group__2__Impl4949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__3__Impl_in_rule__CRS__Group__34980 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__4_in_rule__CRS__Group__34983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__TypeAssignment_3_in_rule__CRS__Group__3__Impl5010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__4__Impl_in_rule__CRS__Group__45040 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__5_in_rule__CRS__Group__45043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__CRS__Group__4__Impl5071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__5__Impl_in_rule__CRS__Group__55102 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__6_in_rule__CRS__Group__55105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__CRS__Group__5__Impl5133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__6__Impl_in_rule__CRS__Group__65164 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__7_in_rule__CRS__Group__65167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__CRS__Group__6__Impl5195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__7__Impl_in_rule__CRS__Group__75226 = new BitSet(new long[]{0x0000008000100000L});
    public static final BitSet FOLLOW_rule__CRS__Group__8_in_rule__CRS__Group__75229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__CRS__Group__7__Impl5257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__8__Impl_in_rule__CRS__Group__85288 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__9_in_rule__CRS__Group__85291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Alternatives_8_in_rule__CRS__Group__8__Impl5318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__9__Impl_in_rule__CRS__Group__95348 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__CRS__Group__10_in_rule__CRS__Group__95351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__CRS__Group__9__Impl5379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group__10__Impl_in_rule__CRS__Group__105410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__CRS__Group__10__Impl5438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__0__Impl_in_rule__CRS__Group_8_0__05491 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__1_in_rule__CRS__Group_8_0__05494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__CRS__Group_8_0__0__Impl5522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__1__Impl_in_rule__CRS__Group_8_0__15553 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__2_in_rule__CRS__Group_8_0__15556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__CRS__Group_8_0__1__Impl5584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_0__2__Impl_in_rule__CRS__Group_8_0__25615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__NameAssignment_8_0_2_in_rule__CRS__Group_8_0__2__Impl5642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__0__Impl_in_rule__CRS__Group_8_1__05678 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__1_in_rule__CRS__Group_8_1__05681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rule__CRS__Group_8_1__0__Impl5709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__1__Impl_in_rule__CRS__Group_8_1__15740 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__2_in_rule__CRS__Group_8_1__15743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__CRS__Group_8_1__1__Impl5771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__2__Impl_in_rule__CRS__Group_8_1__25802 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__3_in_rule__CRS__Group_8_1__25805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__CRS__Group_8_1__2__Impl5832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__3__Impl_in_rule__CRS__Group_8_1__35861 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__4_in_rule__CRS__Group_8_1__35864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__CRS__Group_8_1__3__Impl5892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__4__Impl_in_rule__CRS__Group_8_1__45923 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__5_in_rule__CRS__Group_8_1__45926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__CRS__Group_8_1__4__Impl5954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__5__Impl_in_rule__CRS__Group_8_1__55985 = new BitSet(new long[]{0x0000000001C00000L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__6_in_rule__CRS__Group_8_1__55988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__CRS__Group_8_1__5__Impl6016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__Group_8_1__6__Impl_in_rule__CRS__Group_8_1__66047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CRS__LinkCRSTypeAssignment_8_1_6_in_rule__CRS__Group_8_1__6__Impl6074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group__0__Impl_in_rule__CoordList__Group__06118 = new BitSet(new long[]{0x0000020000000010L});
    public static final BitSet FOLLOW_rule__CoordList__Group__1_in_rule__CoordList__Group__06121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__CoordList__Group__0__Impl6149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group__1__Impl_in_rule__CoordList__Group__16180 = new BitSet(new long[]{0x0000004020000000L});
    public static final BitSet FOLLOW_rule__CoordList__Group__2_in_rule__CoordList__Group__16183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__CoordsAssignment_1_in_rule__CoordList__Group__1__Impl6210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group__2__Impl_in_rule__CoordList__Group__26240 = new BitSet(new long[]{0x0000004020000000L});
    public static final BitSet FOLLOW_rule__CoordList__Group__3_in_rule__CoordList__Group__26243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group_2__0_in_rule__CoordList__Group__2__Impl6270 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group__3__Impl_in_rule__CoordList__Group__36301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__CoordList__Group__3__Impl6329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group_2__0__Impl_in_rule__CoordList__Group_2__06368 = new BitSet(new long[]{0x0000020000000010L});
    public static final BitSet FOLLOW_rule__CoordList__Group_2__1_in_rule__CoordList__Group_2__06371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__CoordList__Group_2__0__Impl6399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__Group_2__1__Impl_in_rule__CoordList__Group_2__16430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordList__CoordsAssignment_2_1_in_rule__CoordList__Group_2__1__Impl6457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__0__Impl_in_rule__Geometry__Group__06491 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__1_in_rule__Geometry__Group__06494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__1__Impl_in_rule__Geometry__Group__16552 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__2_in_rule__Geometry__Group__16555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Geometry__Group__1__Impl6583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__2__Impl_in_rule__Geometry__Group__26614 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__3_in_rule__Geometry__Group__26617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Geometry__Group__2__Impl6645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__3__Impl_in_rule__Geometry__Group__36676 = new BitSet(new long[]{0x000000000003F800L});
    public static final BitSet FOLLOW_rule__Geometry__Group__4_in_rule__Geometry__Group__36679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Geometry__Group__3__Impl6707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__4__Impl_in_rule__Geometry__Group__46738 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__5_in_rule__Geometry__Group__46741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__TypeAssignment_4_in_rule__Geometry__Group__4__Impl6768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__5__Impl_in_rule__Geometry__Group__56798 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__6_in_rule__Geometry__Group__56801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Geometry__Group__5__Impl6829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__6__Impl_in_rule__Geometry__Group__66860 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__7_in_rule__Geometry__Group__66863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rule__Geometry__Group__6__Impl6891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__7__Impl_in_rule__Geometry__Group__76922 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__8_in_rule__Geometry__Group__76925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Geometry__Group__7__Impl6953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__8__Impl_in_rule__Geometry__Group__86984 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__9_in_rule__Geometry__Group__86987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__Geometry__Group__8__Impl7015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__9__Impl_in_rule__Geometry__Group__97046 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__10_in_rule__Geometry__Group__97049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__CoordsAssignment_9_in_rule__Geometry__Group__9__Impl7076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__10__Impl_in_rule__Geometry__Group__107106 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__Geometry__Group__11_in_rule__Geometry__Group__107109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__Geometry__Group__10__Impl7137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Geometry__Group__11__Impl_in_rule__Geometry__Group__117168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Geometry__Group__11__Impl7196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__0__Impl_in_rule__CoordArray__Group__07251 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__1_in_rule__CoordArray__Group__07254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__1__Impl_in_rule__CoordArray__Group__17312 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__2_in_rule__CoordArray__Group__17315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__CoordArray__Group__1__Impl7343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__2__Impl_in_rule__CoordArray__Group__27374 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__3_in_rule__CoordArray__Group__27377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2__0_in_rule__CoordArray__Group__2__Impl7404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group__3__Impl_in_rule__CoordArray__Group__37435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__CoordArray__Group__3__Impl7463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2__0__Impl_in_rule__CoordArray__Group_2__07502 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2__1_in_rule__CoordArray__Group_2__07505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__CoordsAssignment_2_0_in_rule__CoordArray__Group_2__0__Impl7532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2__1__Impl_in_rule__CoordArray__Group_2__17562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2_1__0_in_rule__CoordArray__Group_2__1__Impl7589 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2_1__0__Impl_in_rule__CoordArray__Group_2_1__07624 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2_1__1_in_rule__CoordArray__Group_2_1__07627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__CoordArray__Group_2_1__0__Impl7655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__Group_2_1__1__Impl_in_rule__CoordArray__Group_2_1__17686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CoordArray__CoordsAssignment_2_1_1_in_rule__CoordArray__Group_2_1__1__Impl7713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group__0__Impl_in_rule__Style__Group__07747 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Style__Group__1_in_rule__Style__Group__07750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Style__Group__0__Impl7778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group__1__Impl_in_rule__Style__Group__17809 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_rule__Style__Group__2_in_rule__Style__Group__17812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__PropertyAssignment_1_in_rule__Style__Group__1__Impl7839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group__2__Impl_in_rule__Style__Group__27869 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_rule__Style__Group__3_in_rule__Style__Group__27872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group_2__0_in_rule__Style__Group__2__Impl7899 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__Style__Group__3__Impl_in_rule__Style__Group__37930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Style__Group__3__Impl7958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group_2__0__Impl_in_rule__Style__Group_2__07997 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Style__Group_2__1_in_rule__Style__Group_2__08000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Style__Group_2__0__Impl8028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__Group_2__1__Impl_in_rule__Style__Group_2__18059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Style__PropertyAssignment_2_1_in_rule__Style__Group_2__1__Impl8086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__0__Impl_in_rule__Feature__Group__08120 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__1_in_rule__Feature__Group__08123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Feature__Group__0__Impl8151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__1__Impl_in_rule__Feature__Group__18182 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__2_in_rule__Feature__Group__18185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Feature__Group__1__Impl8213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__2__Impl_in_rule__Feature__Group__28244 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__3_in_rule__Feature__Group__28247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Feature__Group__2__Impl8275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__3__Impl_in_rule__Feature__Group__38306 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__4_in_rule__Feature__Group__38309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__GeometryAssignment_3_in_rule__Feature__Group__3__Impl8336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__4__Impl_in_rule__Feature__Group__48366 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__5_in_rule__Feature__Group__48369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Feature__Group__4__Impl8397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__5__Impl_in_rule__Feature__Group__58428 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__6_in_rule__Feature__Group__58431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__Feature__Group__5__Impl8459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__6__Impl_in_rule__Feature__Group__68490 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__7_in_rule__Feature__Group__68493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Feature__Group__6__Impl8521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__7__Impl_in_rule__Feature__Group__78552 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__Feature__Group__8_in_rule__Feature__Group__78555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__PropertiesAssignment_7_in_rule__Feature__Group__7__Impl8582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Group__8__Impl_in_rule__Feature__Group__88612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Feature__Group__8__Impl8640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group__0__Impl_in_rule__Struct__Group__08689 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Struct__Group__1_in_rule__Struct__Group__08692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group__1__Impl_in_rule__Struct__Group__18750 = new BitSet(new long[]{0x0000000004000020L});
    public static final BitSet FOLLOW_rule__Struct__Group__2_in_rule__Struct__Group__18753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Struct__Group__1__Impl8781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group__2__Impl_in_rule__Struct__Group__28812 = new BitSet(new long[]{0x0000000004000020L});
    public static final BitSet FOLLOW_rule__Struct__Group__3_in_rule__Struct__Group__28815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2__0_in_rule__Struct__Group__2__Impl8842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group__3__Impl_in_rule__Struct__Group__38873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Struct__Group__3__Impl8901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2__0__Impl_in_rule__Struct__Group_2__08940 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Struct__Group_2__1_in_rule__Struct__Group_2__08943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__PropertyAssignment_2_0_in_rule__Struct__Group_2__0__Impl8970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2__1__Impl_in_rule__Struct__Group_2__19000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2_1__0_in_rule__Struct__Group_2__1__Impl9027 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2_1__0__Impl_in_rule__Struct__Group_2_1__09062 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Struct__Group_2_1__1_in_rule__Struct__Group_2_1__09065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Struct__Group_2_1__0__Impl9093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__Group_2_1__1__Impl_in_rule__Struct__Group_2_1__19124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Struct__PropertyAssignment_2_1_1_in_rule__Struct__Group_2_1__1__Impl9151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__09185 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Property__Group__1_in_rule__Property__Group__09188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__NameAssignment_0_in_rule__Property__Group__0__Impl9215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__19245 = new BitSet(new long[]{0x0000022002000030L});
    public static final BitSet FOLLOW_rule__Property__Group__2_in_rule__Property__Group__19248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Property__Group__1__Impl9276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__29307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__ValueAssignment_2_in_rule__Property__Group__2__Impl9334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group__0__Impl_in_rule__Array__Group__09370 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__Array__Group__1_in_rule__Array__Group__09373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group__1__Impl_in_rule__Array__Group__19431 = new BitSet(new long[]{0x0000026002000030L});
    public static final BitSet FOLLOW_rule__Array__Group__2_in_rule__Array__Group__19434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__Array__Group__1__Impl9462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group__2__Impl_in_rule__Array__Group__29493 = new BitSet(new long[]{0x0000026002000030L});
    public static final BitSet FOLLOW_rule__Array__Group__3_in_rule__Array__Group__29496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2__0_in_rule__Array__Group__2__Impl9523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group__3__Impl_in_rule__Array__Group__39554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__Array__Group__3__Impl9582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2__0__Impl_in_rule__Array__Group_2__09621 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Array__Group_2__1_in_rule__Array__Group_2__09624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__ValuesAssignment_2_0_in_rule__Array__Group_2__0__Impl9651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2__1__Impl_in_rule__Array__Group_2__19681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2_1__0_in_rule__Array__Group_2__1__Impl9708 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2_1__0__Impl_in_rule__Array__Group_2_1__09743 = new BitSet(new long[]{0x0000022002000030L});
    public static final BitSet FOLLOW_rule__Array__Group_2_1__1_in_rule__Array__Group_2_1__09746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Array__Group_2_1__0__Impl9774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__Group_2_1__1__Impl_in_rule__Array__Group_2_1__19805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Array__ValuesAssignment_2_1_1_in_rule__Array__Group_2_1__1__Impl9832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__NINT__Group__0__Impl_in_rule__NINT__Group__09866 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__NINT__Group__1_in_rule__NINT__Group__09869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_rule__NINT__Group__0__Impl9897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__NINT__Group__1__Impl_in_rule__NINT__Group__19928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__NINT__Group__1__Impl9955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group__0__Impl_in_rule__DOUBLE__Group__09988 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group__1_in_rule__DOUBLE__Group__09991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Alternatives_0_in_rule__DOUBLE__Group__0__Impl10018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group__1__Impl_in_rule__DOUBLE__Group__110048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group_1__0_in_rule__DOUBLE__Group__1__Impl10075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group_1__0__Impl_in_rule__DOUBLE__Group_1__010109 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group_1__1_in_rule__DOUBLE__Group_1__010112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_rule__DOUBLE__Group_1__0__Impl10140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DOUBLE__Group_1__1__Impl_in_rule__DOUBLE__Group_1__110171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__DOUBLE__Group_1__1__Impl10198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__0_in_rule__GeoJSON__UnorderedGroup_110232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_0__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_1__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_2__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Alternatives_1_3_in_rule__GeoJSON__UnorderedGroup_1__Impl10594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10686 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10730 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_5__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_6__0_in_rule__GeoJSON__UnorderedGroup_1__Impl10919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_7__0_in_rule__GeoJSON__UnorderedGroup_1__Impl11010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__011069 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__1_in_rule__GeoJSON__UnorderedGroup_1__011072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__111097 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__2_in_rule__GeoJSON__UnorderedGroup_1__111100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__211125 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__3_in_rule__GeoJSON__UnorderedGroup_1__211128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__311153 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__4_in_rule__GeoJSON__UnorderedGroup_1__311156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__411181 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__5_in_rule__GeoJSON__UnorderedGroup_1__411184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__511209 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__6_in_rule__GeoJSON__UnorderedGroup_1__511212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__611237 = new BitSet(new long[]{0x0000001FC8000022L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__7_in_rule__GeoJSON__UnorderedGroup_1__611240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__UnorderedGroup_1__Impl_in_rule__GeoJSON__UnorderedGroup_1__711265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleType_in_rule__GeoJSON__TypeAssignment_1_0_211309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCRS_in_rule__GeoJSON__CrsAssignment_1_1_211340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoordList_in_rule__GeoJSON__BboxAssignment_1_2_211371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_0_211402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_1_211433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometry_in_rule__GeoJSON__GeometryAssignment_1_3_1_3_111464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__GeoJSON__FlatPropertiesAssignment_1_4_011495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStruct_in_rule__GeoJSON__PropertiesAssignment_1_5_211526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStyle_in_rule__GeoJSON__StyleAssignment_1_6_211557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeature_in_rule__GeoJSON__FeaturesAssignment_1_7_3_011588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeature_in_rule__GeoJSON__FeaturesAssignment_1_7_3_1_111619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCRSType_in_rule__CRS__TypeAssignment_311650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__CRS__NameAssignment_8_0_211681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLinkCRSType_in_rule__CRS__LinkCRSTypeAssignment_8_1_611712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDOUBLE_in_rule__CoordList__CoordsAssignment_111743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDOUBLE_in_rule__CoordList__CoordsAssignment_2_111774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometryType_in_rule__Geometry__TypeAssignment_411805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoord_in_rule__Geometry__CoordsAssignment_911836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoord_in_rule__CoordArray__CoordsAssignment_2_011867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCoord_in_rule__CoordArray__CoordsAssignment_2_1_111898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Style__PropertyAssignment_111929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Style__PropertyAssignment_2_111960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGeometry_in_rule__Feature__GeometryAssignment_311991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStruct_in_rule__Feature__PropertiesAssignment_712022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Struct__PropertyAssignment_2_012053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Struct__PropertyAssignment_2_1_112084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Property__NameAssignment_012115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_rule__Property__ValueAssignment_212146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Value__IntValueAssignment_012177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNINT_in_rule__Value__NintValueAssignment_112208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDOUBLE_in_rule__Value__DoubleValueAssignment_212239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Value__StringValueAssignment_312270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArray_in_rule__Value__ArrayValueAssignment_412301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStruct_in_rule__Value__StructValueAssignment_512332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_rule__Array__ValuesAssignment_2_012363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_rule__Array__ValuesAssignment_2_1_112394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GeoJSON__Group_1_4__0_in_synpred1_InternalGeoJSON10727 = new BitSet(new long[]{0x0000000000000002L});

}
