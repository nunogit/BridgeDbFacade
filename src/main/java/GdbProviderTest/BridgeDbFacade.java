package GdbProviderTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperCapabilities;
import org.bridgedb.IDMapperException;
import org.bridgedb.IDMapperStack;
import org.bridgedb.Xref;
import org.bridgedb.bio.Organism;
import org.bridgedb.rdb.GdbProvider;

public class BridgeDbFacade {

	public static void main(String[] args) throws IDMapperException, ClassNotFoundException, IOException {
		BridgeDbFacade bdbf = new BridgeDbFacade();
		bdbf.xrefs("ENSG00000049541");
		//bdbf.xrefs("1053_at");
	}
	
	String xrefs(String identifier) throws IDMapperException, ClassNotFoundException, IOException {
		GdbProvider gdp = GdbProvider.fromConfigFile(new File("/home/nuno/dev/BridgeDb-databases/gdb.config"), true);
		IDMapperStack idstack = gdp.getStack();
		Xref xref = new Xref(identifier);
		
		IDMapperStack orgIdStack = gdp.getStack();
		
		List<IDMapper> mappers = orgIdStack.getMappers();
			
		for(IDMapper mp : mappers) {				
			Set<Xref> xrefset = mp.mapID(xref);
			if(xrefset.size()>0) {
				IDMapperCapabilities idc = mp.getCapabilities();
				System.out.println(idc.getProperty("Series"));
				
				if(xrefset.size()>0)
					System.out.println(xrefset);
				else
					System.out.println("set is 0");
				
				return "-"; 
			}
		}	
		return null;
	}
		
	
	String xrefs_alternative(String identifier) throws IDMapperException, ClassNotFoundException, IOException {
		GdbProvider gdp = GdbProvider.fromConfigFile(new File("/home/nuno/dev/BridgeDb-databases/gdb.config"), true);

		IDMapperStack idstack = gdp.getStack();
		//System.out.println("size: " + idstack.getSize() +  "/n");
		
		//Xref xref1 = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
		//Xref xref2 = new Xref("H7C5Q7", DataSource.getByFullName("Uniprot-TrEMBL"));
		//Xref xref3 = new Xref("1234", DataSource.getBySystemCode("L"));
		
		Xref xref = new Xref(identifier);
		//Xref xref = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
		
		Set<Organism> organismSet = gdp.getOrganisms();
		
		for(Organism org : organismSet) {
			IDMapperStack orgIdStack = gdp.getStack();
						
			//System.out.println("se lixou"+ orgIdStack);
			//if(true) { System.out.println("END"); return null; }
			
			Set<Xref> xrefset = orgIdStack.mapID(xref);

			if(xrefset.size()>0)
				System.out.println(xrefset);
			else
				System.out.println("set is 0");
			
			for(Xref xreft : xrefset) {
				System.out.println("result..........: "+xreft.getId() + " " + xreft.getDataSource() + " " + org.latinName());
				xreft.getDataSource();
			}	
		}
		
		if(true) { System.out.println("END"); return null; }
		return identifier;
	}
}
