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
import org.semanticweb.HermiT.cli.CommandLine;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.util.AutoIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

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
				
				//
				
			}
			
			for (JComboBox haberes : defRows.get(i).getHaberFields()) {
				
			}
			
			//Disjoint Debe y Haber Classes
			OWLAxiom disjointDebeHaberClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDebeHaberClasses);
			ontologyManager.addAxiom(ontology, disjointDebeHaberClassesAxiom);
		}
		
		//Disjoint Def Classes
		OWLAxiom disjointDefClassesAxiom = factory.getOWLDisjointClassesAxiom(disjointDefClasses);
		ontologyManager.addAxiom(ontology, disjointDefClassesAxiom);
		
		for (int i=0; i<inputRows.size(); i++) {
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
