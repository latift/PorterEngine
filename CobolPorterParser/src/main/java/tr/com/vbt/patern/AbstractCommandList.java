package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.token.AbstractToken;

public abstract class AbstractCommandList implements CommandList {
	
	protected PaternManager paternManager;

	protected List<AbstractCommand> commandList=new ArrayList<AbstractCommand>();
	
	public List<AbstractCommand> commandListWithIncludedViewVariables=new ArrayList<AbstractCommand>();
	
	public List<AbstractCommand> commandListWithIncludedVariables=new ArrayList<AbstractCommand>();
	
	protected List<Levelable> redefinedCommandList;
	
	protected Set<String> undefinedCommandSet=new HashSet<String>();
	
	protected List<AbstractToken> tokenListesi;

	
	
	public List<AbstractCommand> getCommandList() {
		return commandList;
	}
	

	public void setCommandList(List<AbstractCommand> commandList) {
		this.commandList = commandList;
	}

	public List<AbstractToken> getTokenListesi() {
		return tokenListesi;
	}

	public void setTokenListesi(List<AbstractToken> tokenListesi) {
		this.tokenListesi = tokenListesi;
	}

	@Override
	public PaternManager getPaternManager() {
		return paternManager;
	}

	@Override
	public void setPaternManager(PaternManager paternManager) {
		this.paternManager = paternManager;
	}

	public Set<String> getUndefinedCommandSet() {
		return undefinedCommandSet;
	}


	public List<AbstractCommand> getCommandListWithIncludedViewVariables() {
		return commandListWithIncludedViewVariables;
	}


	public List<AbstractCommand> getCommandListWithIncludedVariables() {
		return commandListWithIncludedVariables;
	}
	
	
	
}
