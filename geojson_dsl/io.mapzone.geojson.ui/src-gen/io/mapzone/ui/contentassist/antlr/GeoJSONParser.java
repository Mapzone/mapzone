/*
 * generated by Xtext
 */
package io.mapzone.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import io.mapzone.services.GeoJSONGrammarAccess;

public class GeoJSONParser extends AbstractContentAssistParser {
	
	@Inject
	private GeoJSONGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected io.mapzone.ui.contentassist.antlr.internal.InternalGeoJSONParser createParser() {
		io.mapzone.ui.contentassist.antlr.internal.InternalGeoJSONParser result = new io.mapzone.ui.contentassist.antlr.internal.InternalGeoJSONParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getGeoJSONAccess().getAlternatives_1_3(), "rule__GeoJSON__Alternatives_1_3");
					put(grammarAccess.getCRSAccess().getAlternatives_8(), "rule__CRS__Alternatives_8");
					put(grammarAccess.getCoordAccess().getAlternatives(), "rule__Coord__Alternatives");
					put(grammarAccess.getValueAccess().getAlternatives(), "rule__Value__Alternatives");
					put(grammarAccess.getDOUBLEAccess().getAlternatives_0(), "rule__DOUBLE__Alternatives_0");
					put(grammarAccess.getTypeAccess().getAlternatives(), "rule__Type__Alternatives");
					put(grammarAccess.getCRSTypeAccess().getAlternatives(), "rule__CRSType__Alternatives");
					put(grammarAccess.getLinkCRSTypeAccess().getAlternatives(), "rule__LinkCRSType__Alternatives");
					put(grammarAccess.getGeometryTypeAccess().getAlternatives(), "rule__GeometryType__Alternatives");
					put(grammarAccess.getGeoJSONAccess().getGroup(), "rule__GeoJSON__Group__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_0(), "rule__GeoJSON__Group_1_0__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_1(), "rule__GeoJSON__Group_1_1__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_2(), "rule__GeoJSON__Group_1_2__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_3_0(), "rule__GeoJSON__Group_1_3_0__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_3_1(), "rule__GeoJSON__Group_1_3_1__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_3_1_3(), "rule__GeoJSON__Group_1_3_1_3__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_4(), "rule__GeoJSON__Group_1_4__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_5(), "rule__GeoJSON__Group_1_5__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_6(), "rule__GeoJSON__Group_1_6__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_7(), "rule__GeoJSON__Group_1_7__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_7_3(), "rule__GeoJSON__Group_1_7_3__0");
					put(grammarAccess.getGeoJSONAccess().getGroup_1_7_3_1(), "rule__GeoJSON__Group_1_7_3_1__0");
					put(grammarAccess.getCRSAccess().getGroup(), "rule__CRS__Group__0");
					put(grammarAccess.getCRSAccess().getGroup_8_0(), "rule__CRS__Group_8_0__0");
					put(grammarAccess.getCRSAccess().getGroup_8_1(), "rule__CRS__Group_8_1__0");
					put(grammarAccess.getCoordListAccess().getGroup(), "rule__CoordList__Group__0");
					put(grammarAccess.getCoordListAccess().getGroup_2(), "rule__CoordList__Group_2__0");
					put(grammarAccess.getGeometryAccess().getGroup(), "rule__Geometry__Group__0");
					put(grammarAccess.getCoordArrayAccess().getGroup(), "rule__CoordArray__Group__0");
					put(grammarAccess.getCoordArrayAccess().getGroup_2(), "rule__CoordArray__Group_2__0");
					put(grammarAccess.getCoordArrayAccess().getGroup_2_1(), "rule__CoordArray__Group_2_1__0");
					put(grammarAccess.getStyleAccess().getGroup(), "rule__Style__Group__0");
					put(grammarAccess.getStyleAccess().getGroup_2(), "rule__Style__Group_2__0");
					put(grammarAccess.getFeatureAccess().getGroup(), "rule__Feature__Group__0");
					put(grammarAccess.getStructAccess().getGroup(), "rule__Struct__Group__0");
					put(grammarAccess.getStructAccess().getGroup_2(), "rule__Struct__Group_2__0");
					put(grammarAccess.getStructAccess().getGroup_2_1(), "rule__Struct__Group_2_1__0");
					put(grammarAccess.getPropertyAccess().getGroup(), "rule__Property__Group__0");
					put(grammarAccess.getArrayAccess().getGroup(), "rule__Array__Group__0");
					put(grammarAccess.getArrayAccess().getGroup_2(), "rule__Array__Group_2__0");
					put(grammarAccess.getArrayAccess().getGroup_2_1(), "rule__Array__Group_2_1__0");
					put(grammarAccess.getNINTAccess().getGroup(), "rule__NINT__Group__0");
					put(grammarAccess.getDOUBLEAccess().getGroup(), "rule__DOUBLE__Group__0");
					put(grammarAccess.getDOUBLEAccess().getGroup_1(), "rule__DOUBLE__Group_1__0");
					put(grammarAccess.getGeoJSONAccess().getTypeAssignment_1_0_2(), "rule__GeoJSON__TypeAssignment_1_0_2");
					put(grammarAccess.getGeoJSONAccess().getCrsAssignment_1_1_2(), "rule__GeoJSON__CrsAssignment_1_1_2");
					put(grammarAccess.getGeoJSONAccess().getBboxAssignment_1_2_2(), "rule__GeoJSON__BboxAssignment_1_2_2");
					put(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_0_2(), "rule__GeoJSON__GeometryAssignment_1_3_0_2");
					put(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_2(), "rule__GeoJSON__GeometryAssignment_1_3_1_2");
					put(grammarAccess.getGeoJSONAccess().getGeometryAssignment_1_3_1_3_1(), "rule__GeoJSON__GeometryAssignment_1_3_1_3_1");
					put(grammarAccess.getGeoJSONAccess().getFlatPropertiesAssignment_1_4_0(), "rule__GeoJSON__FlatPropertiesAssignment_1_4_0");
					put(grammarAccess.getGeoJSONAccess().getPropertiesAssignment_1_5_2(), "rule__GeoJSON__PropertiesAssignment_1_5_2");
					put(grammarAccess.getGeoJSONAccess().getStyleAssignment_1_6_2(), "rule__GeoJSON__StyleAssignment_1_6_2");
					put(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_0(), "rule__GeoJSON__FeaturesAssignment_1_7_3_0");
					put(grammarAccess.getGeoJSONAccess().getFeaturesAssignment_1_7_3_1_1(), "rule__GeoJSON__FeaturesAssignment_1_7_3_1_1");
					put(grammarAccess.getCRSAccess().getTypeAssignment_3(), "rule__CRS__TypeAssignment_3");
					put(grammarAccess.getCRSAccess().getNameAssignment_8_0_2(), "rule__CRS__NameAssignment_8_0_2");
					put(grammarAccess.getCRSAccess().getLinkCRSTypeAssignment_8_1_6(), "rule__CRS__LinkCRSTypeAssignment_8_1_6");
					put(grammarAccess.getCoordListAccess().getCoordsAssignment_1(), "rule__CoordList__CoordsAssignment_1");
					put(grammarAccess.getCoordListAccess().getCoordsAssignment_2_1(), "rule__CoordList__CoordsAssignment_2_1");
					put(grammarAccess.getGeometryAccess().getTypeAssignment_4(), "rule__Geometry__TypeAssignment_4");
					put(grammarAccess.getGeometryAccess().getCoordsAssignment_9(), "rule__Geometry__CoordsAssignment_9");
					put(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_0(), "rule__CoordArray__CoordsAssignment_2_0");
					put(grammarAccess.getCoordArrayAccess().getCoordsAssignment_2_1_1(), "rule__CoordArray__CoordsAssignment_2_1_1");
					put(grammarAccess.getStyleAccess().getPropertyAssignment_1(), "rule__Style__PropertyAssignment_1");
					put(grammarAccess.getStyleAccess().getPropertyAssignment_2_1(), "rule__Style__PropertyAssignment_2_1");
					put(grammarAccess.getFeatureAccess().getGeometryAssignment_3(), "rule__Feature__GeometryAssignment_3");
					put(grammarAccess.getFeatureAccess().getPropertiesAssignment_7(), "rule__Feature__PropertiesAssignment_7");
					put(grammarAccess.getStructAccess().getPropertyAssignment_2_0(), "rule__Struct__PropertyAssignment_2_0");
					put(grammarAccess.getStructAccess().getPropertyAssignment_2_1_1(), "rule__Struct__PropertyAssignment_2_1_1");
					put(grammarAccess.getPropertyAccess().getNameAssignment_0(), "rule__Property__NameAssignment_0");
					put(grammarAccess.getPropertyAccess().getValueAssignment_2(), "rule__Property__ValueAssignment_2");
					put(grammarAccess.getValueAccess().getIntValueAssignment_0(), "rule__Value__IntValueAssignment_0");
					put(grammarAccess.getValueAccess().getNintValueAssignment_1(), "rule__Value__NintValueAssignment_1");
					put(grammarAccess.getValueAccess().getDoubleValueAssignment_2(), "rule__Value__DoubleValueAssignment_2");
					put(grammarAccess.getValueAccess().getStringValueAssignment_3(), "rule__Value__StringValueAssignment_3");
					put(grammarAccess.getValueAccess().getArrayValueAssignment_4(), "rule__Value__ArrayValueAssignment_4");
					put(grammarAccess.getValueAccess().getStructValueAssignment_5(), "rule__Value__StructValueAssignment_5");
					put(grammarAccess.getArrayAccess().getValuesAssignment_2_0(), "rule__Array__ValuesAssignment_2_0");
					put(grammarAccess.getArrayAccess().getValuesAssignment_2_1_1(), "rule__Array__ValuesAssignment_2_1_1");
					put(grammarAccess.getGeoJSONAccess().getUnorderedGroup_1(), "rule__GeoJSON__UnorderedGroup_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			io.mapzone.ui.contentassist.antlr.internal.InternalGeoJSONParser typedParser = (io.mapzone.ui.contentassist.antlr.internal.InternalGeoJSONParser) parser;
			typedParser.entryRuleGeoJSON();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public GeoJSONGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(GeoJSONGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
