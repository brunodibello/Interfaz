package app;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

public class HermitHelper {
	
	private static OWLOntologyManager ontologyManager;
	
	private static void runHermit(Configuration config, OWLOntology ontology) {
		Reasoner hermit = new Reasoner(config, ontology);
        HashSet<InferenceType> inferences = new HashSet<InferenceType>();
        inferences.add(InferenceType.CLASS_HIERARCHY);
        hermit.precomputeInferences(inferences.toArray(new InferenceType[0]));
	}
	
	private static OWLOntology getBaseOntology() {
		try {
			ontologyManager = OWLManager.createOWLOntologyManager();
			URI base = new URI("file", System.getProperty("user.dir") + "/", null);
			IRI iri = IRI.create((URI)base.resolve("../../../../Desktop/github/ProyGrado_Hermit_143456/ontologias/Prototipo/ont0.owl"));
			return ontologyManager.loadOntology(iri);
		} catch(Exception e) {
			System.out.println("Error getting base Ontology");
		}
		return null;
	}
	
	private static Configuration getConfiguration() {
		Configuration config = new Configuration();
        config.useDisjunctionLearning = true;
        config.throwInconsistentOntologyException = true;
        config.blockingSignatureCacheType = Configuration.BlockingSignatureCacheType.CACHED;
        config.blockingStrategyType = Configuration.BlockingStrategyType.OPTIMAL;
        config.directBlockingType = Configuration.DirectBlockingType.OPTIMAL;
        config.existentialStrategyType = Configuration.ExistentialStrategyType.CREATION_ORDER;
        return config;
	}
	
	private static void modifyOntology(List<DefRow> defRows, List<InputRow> inputRows, OWLOntology ontology) {

		OWLDataFactory factory = OWLManager.getOWLDataFactory();
		Set<OWLClassExpression> disjointDefClasses = new HashSet<OWLClassExpression>();
		Set<OWLNamedIndividual> differentNamedIndividuals = new HashSet<OWLNamedIndividual>();
		Set<OWLClassExpression> disjointDebeHaberClasses = new HashSet<OWLClassExpression>();
		
		for (int i=0; i<defRows.size(); i++) {
			
			if (!defRows.get(i).getAsientoField().getText().toString().equals("")) {
				
				//DX Class Declaration
				OWLEntity DX_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("D"+i));
				disjointDefClasses.add((OWLClassExpression) DX_Class);
				OWLAxiom declare_DX = factory.getOWLDeclarationAxiom(DX_Class);
				ontologyManager.addAxiom(ontology, declare_DX);
				
				//dx individual Declaration
				OWLEntity dx_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("d"+i));
				differentNamedIndividuals.add((OWLNamedIndividual) dx_NamedIndividual);
				OWLAxiom declare_dx = factory.getOWLDeclarationAxiom(dx_NamedIndividual);
				ontologyManager.addAxiom(ontology, declare_dx);
				
				//SubClass DX Entity Axiom
				OWLEntity Entry_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#Entry"));
				OWLAxiom subClass_DX_Entry = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, (OWLClassExpression) Entry_Class);
				ontologyManager.addAxiom(ontology, subClass_DX_Entry);
				
				//ClassAssertion EntryDef dx
				OWLEntity EntryDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#EntryDef"));
				OWLAxiom classAssertion_EntryDef_dx = factory.getOWLClassAssertionAxiom((OWLClassExpression) EntryDef_Class, (OWLNamedIndividual) dx_NamedIndividual);
				ontologyManager.addAxiom(ontology, classAssertion_EntryDef_dx);
				
				//DataPropertyAssertion commentDef dx DAX
				OWLEntity commentDef_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("#commentDef"));
				OWLLiteral DAC_Literal = factory.getOWLLiteral(defRows.get(i).getAsientoField().getText());
				OWLAxiom dataPropertyAssertion_commentDef_dx_DAX = factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) commentDef_DataProperty, (OWLNamedIndividual) dx_NamedIndividual, DAC_Literal);
				ontologyManager.addAxiom(ontology, dataPropertyAssertion_commentDef_dx_DAX);
				
				//Metamodelling DX dx
				OWLAxiom metamodelling_DX_dx = factory.getOWLMetamodellingAxiom((OWLClassExpression) DX_Class, (OWLNamedIndividual) dx_NamedIndividual);
				ontologyManager.addAxiom(ontology, metamodelling_DX_dx);
				
				Set<OWLClassExpression> unionDebeClasses = new HashSet<OWLClassExpression>();
							
				for (int j=0; j<defRows.get(i).getDebeFields().size(); j++) {
					
					//Declaration DDij
					OWLEntity DDij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DD"+i+j));
					unionDebeClasses.add((OWLClassExpression) DDij_Class);
					disjointDebeHaberClasses.add((OWLClassExpression) DDij_Class);
					OWLAxiom declare_DDij = factory.getOWLDeclarationAxiom(DDij_Class);
					ontologyManager.addAxiom(ontology, declare_DDij);
					
					//ddij individual Declaration
					OWLEntity ddij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("dd"+i+j));
					differentNamedIndividuals.add((OWLNamedIndividual) ddij_NamedIndividual);
					OWLAxiom declare_ddij = factory.getOWLDeclarationAxiom(ddij_NamedIndividual);
					ontologyManager.addAxiom(ontology, declare_ddij);
					
					//SubClassOf DDij Det
					OWLEntity Det_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#Det"));
					OWLAxiom subClass_DDij_Det = factory.getOWLSubClassOfAxiom((OWLClassExpression) DDij_Class, (OWLClassExpression) Det_Class);
					ontologyManager.addAxiom(ontology, subClass_DDij_Det);
					
					//ClassAssertion DetDef ddij
					OWLEntity DetDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#DetDef"));
					OWLAxiom classAssertion_DetDef_ddij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DetDef_Class, (OWLNamedIndividual) ddij_NamedIndividual);
					ontologyManager.addAxiom(ontology, classAssertion_DetDef_ddij);
					
					//ObjectPropertyAssertion detailDefD dx ddij
					OWLEntity detailDefD_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailDefD"));
					OWLAxiom objectPropertyAssertion_detailDefD_dx_ddij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailDefD_ObjectProperty, (OWLNamedIndividual) dx_NamedIndividual, (OWLNamedIndividual) ddij_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailDefD_dx_ddij);
					
					//ObjectPropertyAssertion detailDefD dx ddij
					OWLEntity account_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#account"));
					OWLEntity ddij_value_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("#"+defRows.get(i).getDebeFields().get(j).getSelectedItem().toString()));
					differentNamedIndividuals.add((OWLNamedIndividual) ddij_value_NamedIndividual);
					OWLAxiom objectPropertyAssertion_account_dx_ddij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) account_ObjectProperty, (OWLNamedIndividual) ddij_NamedIndividual, (OWLNamedIndividual) ddij_value_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_account_dx_ddij);
					
					//Metamodelling ddij DDij
					OWLAxiom metamodelling_DDij_ddij = factory.getOWLMetamodellingAxiom((OWLClassExpression) DDij_Class, (OWLNamedIndividual) ddij_NamedIndividual);
					ontologyManager.addAxiom(ontology, metamodelling_DDij_ddij);
					
				}
				
				//SubClassOf DX ObjectAllValuesFrom detailD DDij
				OWLEntity detailD_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailD"));
				OWLObjectAllValuesFrom objectAllValuesFrom_detailD_DDij;
				if (unionDebeClasses.size() == 1) {
					objectAllValuesFrom_detailD_DDij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailD_ObjectProperty, (OWLClassExpression) unionDebeClasses.toArray()[0]);
				} else {
					objectAllValuesFrom_detailD_DDij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailD_ObjectProperty, factory.getOWLObjectUnionOf(unionDebeClasses));
				}
				OWLAxiom subClassOf_DX_objectAllValuesFrom_detailD_DDij = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, objectAllValuesFrom_detailD_DDij);
				ontologyManager.addAxiom(ontology, subClassOf_DX_objectAllValuesFrom_detailD_DDij);
				
				Set<OWLClassExpression> unionHaberClasses = new HashSet<OWLClassExpression>();
				
				for (int j=0; j<defRows.get(i).getHaberFields().size(); j++) {
					
					//Declaration DHij
					OWLEntity DHij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DH"+i+j));
					unionHaberClasses.add((OWLClassExpression) DHij_Class);
					disjointDebeHaberClasses.add((OWLClassExpression) DHij_Class);
					OWLAxiom declare_DHij = factory.getOWLDeclarationAxiom(DHij_Class);
					ontologyManager.addAxiom(ontology, declare_DHij);
					
					//dhij individual Declaration
					OWLEntity dhij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("dh"+i+j));
					differentNamedIndividuals.add((OWLNamedIndividual) dhij_NamedIndividual);
					OWLAxiom declare_dhij = factory.getOWLDeclarationAxiom(dhij_NamedIndividual);
					ontologyManager.addAxiom(ontology, declare_dhij);
					
					//SubClassOf DHij Det
					OWLEntity Det_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#Det"));
					OWLAxiom subClass_DHij_Det = factory.getOWLSubClassOfAxiom((OWLClassExpression) DHij_Class, (OWLClassExpression) Det_Class);
					ontologyManager.addAxiom(ontology, subClass_DHij_Det);
					
					//ClassAssertion DetDef dhij
					OWLEntity DetDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("#DetDef"));
					OWLAxiom classAssertion_DetDef_dhij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DetDef_Class, (OWLNamedIndividual) dhij_NamedIndividual);
					ontologyManager.addAxiom(ontology, classAssertion_DetDef_dhij);
					
					//ObjectPropertyAssertion detailDefC dx dhij
					OWLEntity detailDefC_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailDefC"));
					OWLAxiom objectPropertyAssertion_detailDefC_dx_dhij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailDefC_ObjectProperty, (OWLNamedIndividual) dx_NamedIndividual, (OWLNamedIndividual) dhij_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailDefC_dx_dhij);
					
					//ObjectPropertyAssertion detailDefC dx dhij
					OWLEntity account_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#account"));
					OWLEntity dhij_value_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("#"+defRows.get(i).getHaberFields().get(j).getSelectedItem().toString()));
					differentNamedIndividuals.add((OWLNamedIndividual) dhij_value_NamedIndividual);
					OWLAxiom objectPropertyAssertion_account_dx_dhij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) account_ObjectProperty, (OWLNamedIndividual) dhij_NamedIndividual, (OWLNamedIndividual) dhij_value_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_account_dx_dhij);
					
					//Metamodelling dhij DHij
					OWLAxiom metamodelling_DHij_dhij = factory.getOWLMetamodellingAxiom((OWLClassExpression) DHij_Class, (OWLNamedIndividual) dhij_NamedIndividual);
					ontologyManager.addAxiom(ontology, metamodelling_DHij_dhij);
					
				}
				
				//SubClassOf DX ObjectAllValuesFrom detailC DHij
				OWLEntity detailC_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailC"));
				OWLObjectAllValuesFrom objectAllValuesFrom_detailC_DHij;
				if (unionHaberClasses.size() == 1) {
					objectAllValuesFrom_detailC_DHij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailC_ObjectProperty, (OWLClassExpression) unionHaberClasses.toArray()[0]);
				} else {
					objectAllValuesFrom_detailC_DHij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailC_ObjectProperty, factory.getOWLObjectUnionOf(unionHaberClasses));
				}
				OWLAxiom subClassOf_DX_objectAllValuesFrom_detailC_DHij = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, objectAllValuesFrom_detailC_DHij);
				ontologyManager.addAxiom(ontology, subClassOf_DX_objectAllValuesFrom_detailC_DHij);
				
			}
			
		}
		
		if (!defRows.get(0).getAsientoField().getText().toString().equals("")) {
			//Disjoint Debe y Haber Classes
			OWLEntity DetNotQualified = factory.getOWLEntity(EntityType.CLASS, IRI.create("#DetNotQualified"));
			disjointDebeHaberClasses.add((OWLClassExpression) DetNotQualified);
			OWLAxiom disjointDebeHaberClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDebeHaberClasses);
			ontologyManager.addAxiom(ontology, disjointDebeHaberClassesAxiom);
			
			//Disjoint Def Classes
			OWLAxiom disjointDefClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDefClasses);
			ontologyManager.addAxiom(ontology, disjointDefClassesAxiom);
		}
		
		for (int i=0; i<inputRows.size(); i++) {
			
			if (!inputRows.get(i).getAsientoField().getText().toString().equals("")) {
				
				//ix individual Declaration
				OWLEntity ix_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("i"+i));
				differentNamedIndividuals.add((OWLNamedIndividual) ix_NamedIndividual);
				OWLAxiom declare_ix = factory.getOWLDeclarationAxiom(ix_NamedIndividual);
				ontologyManager.addAxiom(ontology, declare_ix);
				
				//ClassAssertion DX ix 
				OWLEntity DX_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("D"+i));
				OWLAxiom classAssertion_DX_ix = factory.getOWLClassAssertionAxiom((OWLClassExpression) DX_Class, (OWLNamedIndividual) ix_NamedIndividual);
				ontologyManager.addAxiom(ontology, classAssertion_DX_ix);
				
				//DataPropertyAssertion comment ix IAX
				OWLEntity comment_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("#comment"));
				OWLLiteral IAX = factory.getOWLLiteral(inputRows.get(i).getAsientoField().getText());
				OWLAxiom dataPropertyAssertion_comment_ix_IAX = factory.getOWLDataPropertyAssertionAxiom((OWLDataProperty) comment_DataProperty, (OWLNamedIndividual) ix_NamedIndividual, IAX);
				ontologyManager.addAxiom(ontology, dataPropertyAssertion_comment_ix_IAX);
				
				//DataPropertyAssertion dataEntry ix IFX
				OWLEntity dataEntry_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("#dataEntry"));
				OWLLiteral IFX = factory.getOWLLiteral(inputRows.get(i).getFechaField().getText());
				OWLAxiom dataPropertyAssertion_dataEntry_ix_IFX = factory.getOWLDataPropertyAssertionAxiom((OWLDataProperty) dataEntry_DataProperty, (OWLNamedIndividual) ix_NamedIndividual, IFX);
				ontologyManager.addAxiom(ontology, dataPropertyAssertion_dataEntry_ix_IFX);
				
				for (int j=0; j<inputRows.get(i).getDebeFields().size(); j++) {
					
					//Declaration idij
					OWLEntity idij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("id"+i+j));
					differentNamedIndividuals.add((OWLNamedIndividual) idij_NamedIndividual);
					OWLAxiom declare_idij = factory.getOWLDeclarationAxiom(idij_NamedIndividual);
					ontologyManager.addAxiom(ontology, declare_idij);
					
					//ClassAsertion DDij idij
					OWLEntity DDij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DD"+i+j));
					OWLAxiom classAssertion_DDij_idij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DDij_Class, (OWLNamedIndividual) idij_NamedIndividual);
					ontologyManager.addAxiom(ontology, classAssertion_DDij_idij);
					
					//ObjectPropertyAssertion detailD ix idij
					OWLEntity detailD_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailD"));
					OWLAxiom objectPropertyAssertion_detailD_ix_idij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailD_ObjectProperty, (OWLNamedIndividual) ix_NamedIndividual, (OWLNamedIndividual) idij_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailD_ix_idij);
					
					//DataPropertyAssertion amount idij iiijj
					OWLEntity amount_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("#amount"));
					OWLLiteral iiiji = factory.getOWLLiteral(inputRows.get(i).getImporte1Fields().get(j).getText(), OWL2Datatype.XSD_INTEGER);
							//getOWLLiteral(inputRows.get(i).getImporte1Fields().get(j).getText()); //cambiar y traer el importe j
					OWLAxiom dataPropertyAssertion_amount_idij_iiiji = factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) amount_DataProperty, (OWLNamedIndividual) idij_NamedIndividual, iiiji);
					ontologyManager.addAxiom(ontology, dataPropertyAssertion_amount_idij_iiiji);
					
				}
				
				for (int j=0; j<inputRows.get(i).getHaberFields().size(); j++) {
					
					//Declaration idij
					OWLEntity ihij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("ih"+i+j));
					differentNamedIndividuals.add((OWLNamedIndividual) ihij_NamedIndividual);
					OWLAxiom declare_ihij = factory.getOWLDeclarationAxiom(ihij_NamedIndividual);
					ontologyManager.addAxiom(ontology, declare_ihij);
					
					//ClassAsertion DHij ihij
					OWLEntity DHij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DH"+i+j));
					OWLAxiom classAssertion_DHij_ihij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DHij_Class, (OWLNamedIndividual) ihij_NamedIndividual);
					ontologyManager.addAxiom(ontology, classAssertion_DHij_ihij);
					
					//ObjectPropertyAssertion detailC ix ihij
					OWLEntity detailC_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("#detailC"));
					OWLAxiom objectPropertyAssertion_detailC_ix_ihij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailC_ObjectProperty, (OWLNamedIndividual) ix_NamedIndividual, (OWLNamedIndividual) ihij_NamedIndividual);
					ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailC_ix_ihij);
					
					//DataPropertyAssertion amount ihij iiijj
					OWLEntity amount_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("#amount"));
					OWLLiteral iiiji = factory.getOWLLiteral(inputRows.get(i).getImporte2Fields().get(j).getText(), OWL2Datatype.XSD_INTEGER);
							//.getOWLLiteral(inputRows.get(i).getImporte2Fields().get(j).getText()); //cambiar y traer el importe j
					OWLAxiom dataPropertyAssertion_amount_ihij_iiiji = factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) amount_DataProperty, (OWLNamedIndividual) ihij_NamedIndividual, iiiji);
					ontologyManager.addAxiom(ontology, dataPropertyAssertion_amount_ihij_iiiji);
					
				}
				
			}
		}
		
		if (!defRows.get(0).getAsientoField().getText().toString().equals("") || !inputRows.get(0).getAsientoField().getText().toString().equals("")) {
			OWLAxiom differentIndividualsAxiom = factory.getOWLDifferentIndividualsAxiom(differentNamedIndividuals);
			ontologyManager.addAxiom(ontology, differentIndividualsAxiom);
		}
	}
	
	private static void saveModifiedOntology(OWLOntology ontology) {
		File file = new File("modifiedOntology.owl");
		try {
			ontologyManager.saveOntology(ontology, IRI.create(file.toURI()));
		} catch (Exception e) {
			System.out.println("Error saving ontology");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void runReasoner(List<DefRow> defRows, List<InputRow> inputRows) {
		try {
            OWLOntology ontology = getBaseOntology();
            modifyOntology(defRows, inputRows, ontology);
            saveModifiedOntology(ontology);
            Configuration config = getConfiguration();
            runHermit(config, ontology);
            System.out.println("Ontology Consistente");
		} catch (Exception e) {
			System.out.println("Error preparing the ontology");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
