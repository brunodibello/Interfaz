package app;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;

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
		
		for (int i=0; i<defRows.size(); i++) {
			
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
			OWLEntity Entry_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("Entry"));
			OWLAxiom subClass_DX_Entry = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, (OWLClassExpression) Entry_Class);
			ontologyManager.addAxiom(ontology, subClass_DX_Entry);
			
			//ClassAssertion EntryDef dx
			OWLEntity EntryDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("EntryDef"));
			OWLAxiom classAssertion_EntryDef_dx = factory.getOWLClassAssertionAxiom((OWLClassExpression) EntryDef_Class, (OWLNamedIndividual) dx_NamedIndividual);
			ontologyManager.addAxiom(ontology, classAssertion_EntryDef_dx);
			
			//DataPropertyAssertion commentDef dx DAX
			OWLEntity commentDef_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("commendDef"));
			OWLLiteral DAC_Literal = factory.getOWLLiteral(defRows.get(i).getAsientoField().getText());
			OWLAxiom dataPropertyAssertion_commentDef_dx_DAX = factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) commentDef_DataProperty, (OWLNamedIndividual) dx_NamedIndividual, DAC_Literal);
			ontologyManager.addAxiom(ontology, dataPropertyAssertion_commentDef_dx_DAX);
			
			//Metamodelling DX dx
			OWLAxiom metamodelling_DX_dx = factory.getOWLMetamodellingAxiom((OWLClassExpression) DX_Class, (OWLNamedIndividual) dx_NamedIndividual);
			ontologyManager.addAxiom(ontology, metamodelling_DX_dx);
			
			Set<OWLClassExpression> disjointDebeHaberClasses = new HashSet<OWLClassExpression>();
						
			for (int j=0; j<defRows.get(i).getDebeFields().size(); j++) {
				
				//Declaration DDij
				OWLEntity DDij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DD"+i+j));
				disjointDebeHaberClasses.add((OWLClassExpression) DDij_Class);
				OWLAxiom declare_DDij = factory.getOWLDeclarationAxiom(DDij_Class);
				ontologyManager.addAxiom(ontology, declare_DDij);
				
				//ddij individual Declaration
				OWLEntity ddij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("dd"+i+j));
				differentNamedIndividuals.add((OWLNamedIndividual) ddij_NamedIndividual);
				OWLAxiom declare_ddij = factory.getOWLDeclarationAxiom(ddij_NamedIndividual);
				ontologyManager.addAxiom(ontology, declare_ddij);
				
				//SubClassOf DDij Det
				OWLEntity Det_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("Det"));
				OWLAxiom subClass_DDij_Det = factory.getOWLSubClassOfAxiom((OWLClassExpression) DDij_Class, (OWLClassExpression) Det_Class);
				ontologyManager.addAxiom(ontology, subClass_DDij_Det);
				
				//SubClassOf DX ObjectAllValuesFrom detailD DDij
				OWLEntity detailD_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("detailD"));
				OWLObjectAllValuesFrom objectAllValuesFrom_detailD_DDij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailD_ObjectProperty, (OWLClassExpression) DDij_Class);
				OWLAxiom subClassOf_DX_objectAllValuesFrom_detailD_DDij = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, objectAllValuesFrom_detailD_DDij);
				ontologyManager.addAxiom(ontology, subClassOf_DX_objectAllValuesFrom_detailD_DDij);
				
				//ClassAssertion DetDef ddij
				OWLEntity DetDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DetDef"));
				OWLAxiom classAssertion_DetDef_ddij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DetDef_Class, (OWLNamedIndividual) ddij_NamedIndividual);
				ontologyManager.addAxiom(ontology, classAssertion_DetDef_ddij);
				
				//ObjectPropertyAssertion detailDefD dx ddij
				OWLEntity detailDefD_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("detailDefD"));
				OWLAxiom objectPropertyAssertion_detailDefD_dx_ddij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailDefD_ObjectProperty, (OWLNamedIndividual) dx_NamedIndividual, (OWLNamedIndividual) ddij_NamedIndividual);
				ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailDefD_dx_ddij);
				
				//ObjectPropertyAssertion detailDefD dx ddij
				OWLEntity account_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("account"));
				OWLEntity ddij_value_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create(defRows.get(i).getDebeFields().get(j).getName()));
				differentNamedIndividuals.add((OWLNamedIndividual) ddij_value_NamedIndividual);
				OWLAxiom objectPropertyAssertion_account_dx_ddij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) account_ObjectProperty, (OWLNamedIndividual) ddij_NamedIndividual, (OWLNamedIndividual) ddij_value_NamedIndividual);
				ontologyManager.addAxiom(ontology, objectPropertyAssertion_account_dx_ddij);
				
				//Metamodelling ddij DDij
				OWLAxiom metamodelling_DDij_ddij = factory.getOWLMetamodellingAxiom((OWLClassExpression) DDij_Class, (OWLNamedIndividual) ddij_NamedIndividual);
				ontologyManager.addAxiom(ontology, metamodelling_DDij_ddij);
				
			}
			
			for (int j=0; j<defRows.get(i).getHaberFields().size(); j++) {
				
				//Declaration DHij
				OWLEntity DHij_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DH"+i+j));
				disjointDebeHaberClasses.add((OWLClassExpression) DHij_Class);
				OWLAxiom declare_DHij = factory.getOWLDeclarationAxiom(DHij_Class);
				ontologyManager.addAxiom(ontology, declare_DHij);
				
				//dhij individual Declaration
				OWLEntity dhij_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("dh"+i+j));
				differentNamedIndividuals.add((OWLNamedIndividual) dhij_NamedIndividual);
				OWLAxiom declare_dhij = factory.getOWLDeclarationAxiom(dhij_NamedIndividual);
				ontologyManager.addAxiom(ontology, declare_dhij);
				
				//SubClassOf DHij Det
				OWLEntity Det_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("Det"));
				OWLAxiom subClass_DHij_Det = factory.getOWLSubClassOfAxiom((OWLClassExpression) DHij_Class, (OWLClassExpression) Det_Class);
				ontologyManager.addAxiom(ontology, subClass_DHij_Det);
				
				//SubClassOf DX ObjectAllValuesFrom detailC DHij
				OWLEntity detailC_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("detailC"));
				OWLObjectAllValuesFrom objectAllValuesFrom_detailC_DHij = factory.getOWLObjectAllValuesFrom((OWLObjectPropertyExpression) detailC_ObjectProperty, (OWLClassExpression) DHij_Class);
				OWLAxiom subClassOf_DX_objectAllValuesFrom_detailC_DHij = factory.getOWLSubClassOfAxiom((OWLClassExpression) DX_Class, objectAllValuesFrom_detailC_DHij);
				ontologyManager.addAxiom(ontology, subClassOf_DX_objectAllValuesFrom_detailC_DHij);
				
				//ClassAssertion DetDef dhij
				OWLEntity DetDef_Class = factory.getOWLEntity(EntityType.CLASS, IRI.create("DetDef"));
				OWLAxiom classAssertion_DetDef_dhij = factory.getOWLClassAssertionAxiom((OWLClassExpression) DetDef_Class, (OWLNamedIndividual) dhij_NamedIndividual);
				ontologyManager.addAxiom(ontology, classAssertion_DetDef_dhij);
				
				//ObjectPropertyAssertion detailDefC dx dhij
				OWLEntity detailDefC_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("detailDefC"));
				OWLAxiom objectPropertyAssertion_detailDefC_dx_dhij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) detailDefC_ObjectProperty, (OWLNamedIndividual) dx_NamedIndividual, (OWLNamedIndividual) dhij_NamedIndividual);
				ontologyManager.addAxiom(ontology, objectPropertyAssertion_detailDefC_dx_dhij);
				
				//ObjectPropertyAssertion detailDefC dx dhij
				OWLEntity account_ObjectProperty = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, IRI.create("account"));
				OWLEntity dhij_value_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create(defRows.get(i).getHaberFields().get(j).getName()));
				differentNamedIndividuals.add((OWLNamedIndividual) dhij_value_NamedIndividual);
				OWLAxiom objectPropertyAssertion_account_dx_dhij = factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) account_ObjectProperty, (OWLNamedIndividual) dhij_NamedIndividual, (OWLNamedIndividual) dhij_value_NamedIndividual);
				ontologyManager.addAxiom(ontology, objectPropertyAssertion_account_dx_dhij);
				
				//Metamodelling dhij DHij
				OWLAxiom metamodelling_DHij_dhij = factory.getOWLMetamodellingAxiom((OWLClassExpression) DHij_Class, (OWLNamedIndividual) dhij_NamedIndividual);
				ontologyManager.addAxiom(ontology, metamodelling_DHij_dhij);
				
			}
			
			//Disjoint Debe y Haber Classes
			OWLAxiom disjointDebeHaberClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDebeHaberClasses);
			ontologyManager.addAxiom(ontology, disjointDebeHaberClassesAxiom);
		}
		
		//Disjoint Def Classes
		OWLAxiom disjointDefClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDefClasses);
		ontologyManager.addAxiom(ontology, disjointDefClassesAxiom);
		
		for (int i=0; i<inputRows.size(); i++) {
			
			//ix individual Declaration
			OWLEntity ix_NamedIndividual = factory.getOWLEntity(EntityType.NAMED_INDIVIDUAL, IRI.create("i"+i));
			differentNamedIndividuals.add((OWLNamedIndividual) ix_NamedIndividual);
			OWLAxiom declare_ix = factory.getOWLDeclarationAxiom(ix_NamedIndividual);
			ontologyManager.addAxiom(ontology, declare_ix);
			
			//ClassAssertion DX ix ---- Agregar relacion entre el Def y el Input
//			OWLAxiom classAssertion_DX_ix = factory.getOWLClassAssertionAxiom((OWLClassExpression) DX_Class, (OWLNamedIndividual) ix_NamedIndividual);
//			ontologyManager.addAxiom(ontology, classAssertion_DX_ix);
			
			//DataPropertyAssertion comment ix IAX
			OWLEntity comment_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("comment"));
			OWLLiteral IAX = factory.getOWLLiteral(inputRows.get(i).getAsientoField().getText());
			OWLAxiom dataPropertyAssertion_comment_ix_IAX = factory.getOWLDataPropertyAssertionAxiom((OWLDataProperty) comment_DataProperty, (OWLNamedIndividual) ix_NamedIndividual, IAX);
			ontologyManager.addAxiom(ontology, dataPropertyAssertion_comment_ix_IAX);
			
			//DataPropertyAssertion dataEntry ix IFX
			OWLEntity dataEntry_DataProperty = factory.getOWLEntity(EntityType.DATA_PROPERTY, IRI.create("dataEntry"));
			OWLLiteral IFX = factory.getOWLLiteral(inputRows.get(i).getFechaField().getText());
			OWLAxiom dataPropertyAssertion_dataEntry_ix_IFX = factory.getOWLDataPropertyAssertionAxiom((OWLDataProperty) dataEntry_DataProperty, (OWLNamedIndividual) ix_NamedIndividual, IFX);
			ontologyManager.addAxiom(ontology, dataPropertyAssertion_dataEntry_ix_IFX);
			
			for (JComboBox debes : inputRows.get(i).getDebeFields()) {
				
			}
			for (JComboBox haberes : inputRows.get(i).getHaberFields()) {
				
			}
		}
		
		OWLAxiom differentIndividualsAxiom = factory.getOWLDifferentIndividualsAxiom(differentNamedIndividuals);
		ontologyManager.addAxiom(ontology, differentIndividualsAxiom);
	}
	
	public static void runReasoner(List<DefRow> defRows, List<InputRow> inputRows) {
		try {
            OWLOntology ontology = getBaseOntology();
            modifyOntology(defRows, inputRows, ontology);
            Configuration config = getConfiguration();
            runHermit(config, ontology);
		} catch (Exception e) {
			System.out.println("Error preparing the ontology");
		}
	}
}
