package wlst;

import java.util.List;

import objects.Address;
import objects.AdminServer;
import objects.Cluster;
import objects.ConnectionFactory;
import objects.ConnectionPool;
import objects.DataSource;
import objects.DeliveryFailure;
import objects.DistributedQueue;
import objects.Domain;
import objects.Enviroment;
import objects.FileStore;
import objects.JdbcStore;
import objects.JmsModule;
import objects.JmsServer;
import objects.Machine;
import objects.ManagedServer;
import objects.PriorityDestinationKey;
import objects.Queue;
import objects.Subdeployment;
import objects.Target;
import objects.Template;
import objects.Topic;
import objects.WorkManager;
import objects.Transaction;

public class WLSTCommands {

	private final String QUEBRA_DE_LINHA = "\r\n";
	private final String IDENTACAO = "\t";
	
	StringBuilder jythonGerado;

	private WLSTInterpreterWrapper interpreter;

	public WLSTCommands() {
		interpreter = new WLSTInterpreterWrapper();
	}

	public WLSTInterpreterWrapper getInterpreter() {
		return this.interpreter;
	}

	/*
	 * Comandos de utilizacao do usuario
	 */	
	public String createEnviroment(Enviroment env) throws Exception {
		try {
			this.jythonGerado = new StringBuilder();
			
			this.generateCommonFunctions();
			this.defineTemplate(env.getTemplate());
			this.createDomain(env.getDomain());
			
			this.activateProductionMode();
			
			if (env.getDataSources() != null) {
				for (DataSource data : env.getDataSources()) {
					this.createDatasource(data);
				}
			}

			if (env.getFileStores() != null) {
				for (FileStore file : env.getFileStores()) {
					this.createFileStore(file);
				}
			}
			
			if (env.getJdbcStores() != null) {
				for (JdbcStore jdbc : env.getJdbcStores()) {
					this.createJdbcStore(jdbc);
				}
			}
			
			if (env.getJmsServer() != null) {
				for (JmsServer server : env.getJmsServer()) {
					this.createJmsServer(server);
				}
			}

			if (env.getJmsModule() != null) {
				for (JmsModule module : env.getJmsModule()) {
					this.createJmsModule(module);
				}
			}
			
			if (env.getWorkManager() != null) {
				for (WorkManager workManager : env.getWorkManager()) {
					this.createWorkManager(workManager);
				}
			}
			
			this.setOption("OverwriteDomain", "true");
			this.defineWeblogicPassword(env.getWeblogicPassword());
			this.writeDomain(env.getEndereco());

			this.closeTemplate();
			this.exit();
			
			return this.jythonGerado.toString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Comandos de utilizacao do usuario
	 */	
	public String createConfiguration(Enviroment env) throws Exception {
		try {
			this.jythonGerado = new StringBuilder();
			
			this.generateCommonFunctions();
			this.connect(env.getUser(), env.getWeblogicPassword(), env.getUrl());
			
			this.edit();
			
			this.startEdit();
			
			if (env.getDomain() != null){
				this.createDomain(env.getDomain());
			}
						
			if (env.getDataSources() != null) {
				for (DataSource data : env.getDataSources()) {
					this.createDatasource(data);
				}
			}
			
			if (env.getFileStores() != null) {
				for (FileStore file : env.getFileStores()) {
					this.createFileStore(file);
				}
			}
			
			if (env.getJmsServer() != null) {
				for (JmsServer server : env.getJmsServer()) {
					this.createJmsServer(server);
				}
			}
		
			if (env.getJmsModule() != null) {
				for (JmsModule module : env.getJmsModule()) {
					this.createJmsModule(module);
				}
			}
			
			if (env.getWorkManager() != null) {
				for (WorkManager workManager : env.getWorkManager()) {
					this.createWorkManager(workManager);
				}
			}
	
			this.save();
			this.activate(null, null);
			
			this.exit();
			
			return this.jythonGerado.toString();
		} catch (Exception e) {
			throw e;
		}
	}

	//Comandos Internos
	private void generateCommonFunctions() {
		//FUNCTION DE CRIAR JDBC STIRE
		this.jythonGerado.append("def CriarJdbcStore(name, prefixName, target, dataSource):");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando JdbcStore: ' + name ");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'JdbcStore')");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JdbcStore/'+name)");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('PrefixName',prefixName)");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Target',target)");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("DataSource('Target',dataSource)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR FILA				
		this.jythonGerado.append("def CriarFila(type, moduleName, jndiName, subdeployment, priorityDestinationKey):");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Fila: ' + jndiName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/'+ moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);			
			this.jythonGerado.append("if (type == 'QUEUE'):");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("myq=create(jndiName,'Queue')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("else:");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("myq=create(jndiName,'UniformDistributedQueue')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);			
			this.jythonGerado.append("myq.setJNDIName(jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("myq.setSubDeploymentName(subdeployment)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("if (priorityDestinationKey != 'null'):");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("myq.setDestinationKeys(jarray.array([String(priorityDestinationKey)],String))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR DELIVERY FAILURE DAS FILAS				
		this.jythonGerado.append("def CriarDeliveryfailure(moduleName, type, queueName, destinationKey, redeliveryDelay, redeliveryLimit):");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Deliveryfailure para a fila: ' + queueName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("if (type == 'QUEUE'):");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('Queues/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo = cd('DeliveryFailureParams/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setRedeliveryLimit(redeliveryLimit)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setExpirationPolicy('Redirect')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setErrorDestination(getMBean('/JMSSystemResources/'+moduleName+'/JMSResource/'+moduleName+'/Queues/'+destinationKey))");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('..')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('..')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo = cd('DeliveryParamsOverrides/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setRedeliveryDelay(redeliveryDelay)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("else:");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('UniformDistributedQueues/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo = cd('DeliveryFailureParams/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setRedeliveryLimit(redeliveryLimit)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setExpirationPolicy('Redirect')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setErrorDestination(getMBean('/JMSSystemResources/'+moduleName+'/JMSResource/'+moduleName+'/UniformDistributedQueues/'+destinationKey))");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('..')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cd('..')");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo = cd('DeliveryParamsOverrides/' + queueName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setRedeliveryDelay(redeliveryDelay)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);				
					
		//FUNCTION CRIAR ADMIN SERVER		
		this.jythonGerado.append("def CriarAdminServer(name, port, sslPort):");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando AdminServer: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Servers/'+name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ListenPort',port)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('SSL/AdminServer')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Enabled','True')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ListenPort',sslPort)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR CLUSTER
		this.jythonGerado.append("def CriarCluster(name, multicastAddress, port, address):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Cluster: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'Cluster')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Clusters/'+name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('MulticastAddress',multicastAddress)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('MulticastPort',port)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ClusterAddress',address)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR FILE STORE
		this.jythonGerado.append("def CriarFileStore(name, path, target, targetType):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando FileStore: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'FileStore')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('FileStores/'+name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Directory',path)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Targets',jarray.array([ObjectName('com.bea:Name='+target+',Type='+targetType)], ObjectName))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		
		//FUNCTION CRIAR JMS SERVER
			this.jythonGerado.append("def CriarJmsServer(name, persistentStore, target, targetType):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando JmsServer: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'JMSServer')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSServers/'+name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('PersistentStore',ObjectName('com.bea:Name='+persistentStore+',Type=FileStore'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Targets',jarray.array([ObjectName('com.bea:Name='+target+',Type='+targetType)], ObjectName))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);	
		
		//FUNCTION CRIAR TOPIC
		this.jythonGerado.append("def CriarTopic(moduleName, name, subdeployment, redeliveryDelay, redeliveryLimit):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Topic: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("top=create(name,'Topic')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("top.setJNDIName(name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("top.setSubDeploymentName(subdeployment)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Topics/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('DeliveryFailureParams/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('RedeliveryLimit',redeliveryLimit)");
			
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('..')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('..')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('DeliveryParamsOverrides/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('RedeliveryDelay', redeliveryDelay)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR PRIORITY DESTINATION KEY
		this.jythonGerado.append("def CriarPriorityDestinationKey(moduleName, name, sortKey, keyType, direction):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Priority Destination Key: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'DestinationKey')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('DestinationKeys/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Property',sortKey)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('KeyType',keyType)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('SortOrder',direction)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR MANAGED SERVER
		this.jythonGerado.append("def CriarManagedServer(name, port, address, machine, cluster):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Managed Server: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'Server')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Servers/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ListenPort',port)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ListenAddress',address)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR DATASOURCE
		this.jythonGerado.append("def CriarDatasource(jndiName):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Datasource: ' + jndiName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("varDataSource=create(jndiName,'JDBCSystemResource')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JDBCSystemResources/' + jndiName + '/JDBCResource/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Name',jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR JMS MODULE
		this.jythonGerado.append("def CriarJmsModule(name):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando JmsModulo: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'JMSSystemResource')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR CONNECTION FACTORY
		this.jythonGerado.append("def CriarConnectionfactory(moduleName, name, jndiName, reconnectPolicy, maxMessagesPerSession, isServerAffinityEnabled, isXAConnectionFactoryEnabled, sendTimeout):");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Connection Factory: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(name,'ConnectionFactory')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('ConnectionFactories/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('JNDIName',jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('DefaultTargetingEnabled','true')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('ClientParams/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ReconnectPolicy',reconnectPolicy)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('MessagesMaximum',maxMessagesPerSession)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('ConnectionFactories/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('LoadBalancingParams/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ServerAffinityEnabled',isServerAffinityEnabled)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('ConnectionFactories/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('TransactionParams/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('XAConnectionFactoryEnabled',isXAConnectionFactoryEnabled)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/' + moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('ConnectionFactories/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('DefaultDeliveryParams/' + jndiName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('SendTimeout',sendTimeout)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR CONNECTION POOL
		this.jythonGerado.append("def CriarConnectionPool(datasourceName, driverClassName, url, dbPassword, dbUsername, inCapacity, maxCapacity, incrementCapacity, isTestConnReserve, testTableName, shrinkFrequency, connCreationRetryFrequency, incativeConnectionTimeoutSeconds, connReserveTimeout, maxCapacityInit, minCapacityInit, statementCache, testFrequency, supportsGlobal):");		
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Connection Pool para datasource: ' + datasourceName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JDBCDriverParams/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('DriverName',driverClassName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('Url',url)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('PasswordEncrypted',dbPassword)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Properties/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create('user','Property')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Properties/user')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cmo.setValue(dbUsername)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/JDBCSystemResources/' + datasourceName + '/JDBCResource/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JDBCDataSourceParams/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('JNDINames',jarray.array([String(datasourceName)], String))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			
			this.jythonGerado.append("if (supportsGlobal == 'true'):");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("set('GlobalTransactionsProtocol', java.lang.String('None'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);	
			this.jythonGerado.append("else:");	
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("set('GlobalTransactionsProtocol', java.lang.String('LoggingLastResource'))");
				
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/JDBCSystemResources/' + datasourceName + '/JDBCResource/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JDBCConnectionPoolParams/' + datasourceName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('InitialCapacity',inCapacity)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);

			this.jythonGerado.append("set('MaxCapacity',maxCapacityInit)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('MinCapacity',minCapacityInit)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('StatementCacheSize',statementCache)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('TestFrequencySeconds',testFrequency)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			
			this.jythonGerado.append("set('HighestNumWaiters',maxCapacity)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('CapacityIncrement',incrementCapacity)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('TestConnectionsOnReserve',isTestConnReserve)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('TestTableName',testTableName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ShrinkFrequencySeconds',shrinkFrequency)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ConnectionCreationRetryFrequencySeconds',connCreationRetryFrequency)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("if (incativeConnectionTimeoutSeconds != 0):");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("set('InactiveConnectionTimeoutSeconds',incativeConnectionTimeoutSeconds)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('ConnectionReserveTimeoutSeconds',connReserveTimeout)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);			
			this.jythonGerado.append("if(datasourceName == 'sis.TempDataSource'):");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);						
				this.jythonGerado.append("cd('/JDBCSystemResources/'+datasourceName+'/JDBCResource/'+datasourceName+'/JDBCXAParams/'+datasourceName)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("set('XaSetTransactionTimeout', true)");
				this.jythonGerado.append(QUEBRA_DE_LINHA);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append(IDENTACAO);
				this.jythonGerado.append("cmo.setXaTransactionTimeout(600)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR SUBDEPLOYMENT
		this.jythonGerado.append("def CriarSubDeployment(name, subName):");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando SubDeployment: ' + name ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(subName,'SubDeployment')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('SubDeployments/' + subName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR SUBDEPLOYMENT
		this.jythonGerado.append("def CriarTemplate(moduleName, templateName, templateDestinationKey):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando Template: ' + moduleName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('JMSSystemResources/' + moduleName + '/JMSResource/'+ moduleName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(templateName,'Template')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('Templates/' + templateName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("set('DestinationKeys',jarray.array([String(templateDestinationKey)], String))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);

		//FUNCTION CRIAR WORKMANAGER
		this.jythonGerado.append("def CriarWorkManager(workManagerName, domainName):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando WorkManager: ' + workManagerName ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("name = get('Name')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('SelfTuning/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(workManagerName,'WorkManager')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('WorkManagers/' + workManagerName)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cmo.setMinThreadsConstraint(getMBean('/SelfTuning/' + name + '/MinThreadsConstraints/' + workManagerName + 'MinThreads'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cmo.setMaxThreadsConstraint(getMBean('/SelfTuning/' + name + '/MaxThreadsConstraints/' + workManagerName + 'MaxThreads'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);	
			
		//FUNCTION CRIAR WORKMANAGER MIN THREADS
		this.jythonGerado.append("def CriarWorkManagerMinThread(workManagerName, domainName, minCount):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando WorkManager: ' + workManagerName + 'MinThreads' ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("name = get('Name')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('SelfTuning/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(workManagerName+'MinThreads','MinThreadsConstraint')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('MinThreadsConstraints/' + workManagerName+'MinThreads')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cmo.setCount(int(minCount))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			
		//FUNCTION CRIAR WORKMANAGER MAX THREADS		
		this.jythonGerado.append("def CriarWorkManagerMaxThread(workManagerName, domainName, maxCount):");	
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("print 'Criando WorkManager: ' + workManagerName + 'MaxThreads' ");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("name = get('Name')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('SelfTuning/' + name)");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("create(workManagerName+'MaxThreads','MaxThreadsConstraint')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cd('MaxThreadsConstraints/' + workManagerName+'MaxThreads')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(IDENTACAO);
			this.jythonGerado.append("cmo.setCount(int(maxCount))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append(QUEBRA_DE_LINHA);

}
	
	private void exit() {
		this.generateJython("exit()");

	}

	private void writeDomain(String string) {
		this.generateJython("writeDomain('" + string + "')");

	}

	private void setOption(String option, String value) {
		this.generateJython("setOption('" + option + "','" + value + "')");

	}

	private void createJdbcStore(JdbcStore jdbc) {
		this.jythonGerado.append("CriarJdbcStore(");
		
		this.jythonGerado.append(jdbc.getName());
		this.jythonGerado.append(",");
		
		this.jythonGerado.append(jdbc.getPrefixName());
		this.jythonGerado.append(",");
		
		this.jythonGerado.append(jdbc.getTarget());
		this.jythonGerado.append(",");
		
		this.jythonGerado.append(jdbc.getDataSource());
		this.jythonGerado.append(",");		
		
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void defineTemplate(String template) {
		this.readTemplate(template);
	}

	private void defineWeblogicPassword(String password) {
		this.cd("/");
		final String VAR_NAME = "cmo";
		this.genericCommandIntoVariable(VAR_NAME, "cd", "Security/base_domain/User/weblogic");
		this.jythonGerado.append(this.setWeblogicPassword(VAR_NAME, password));
	}

	private void createDomain(Domain domain) {
		this.createAdminServer(domain.getAdminServer());

		if (domain.getMachines() != null) {
			for (Machine machine : domain.getMachines()) {
				this.createMachines(machine);
			}
		}

		if (domain.getManagedServers() != null) {
			for (ManagedServer managedServer : domain.getManagedServers()) {
				this.createManagedServer(managedServer);
			}
		}
		
		if (domain.getClusters() != null) {
			for (Cluster cluster : domain.getClusters()) {
				this.createCluster(cluster);
			}
		}

	}
	private void activateProductionMode(){
		this.jythonGerado.append("cd('/')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("set('ProductionModeEnabled', 'true')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}
	
	private void createWorkManager(WorkManager workManager) {
		
		this.jythonGerado.append("CriarWorkManagerMinThread('");
		this.jythonGerado.append(workManager.getName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(workManager.getMinCount());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		for(Target targetAdd : workManager.getTargets()){
			if(targetAdd.getType().name().equals("Cluster")){
				this.jythonGerado.append("cmo.addTarget(getMBean('/Clusters/");
			} else {
				this.jythonGerado.append("cmo.addTarget(getMBean('/Servers/");
			}
			this.jythonGerado.append(targetAdd.getName());
			this.jythonGerado.append("'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
		}
		
		this.jythonGerado.append("activate()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("edit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("startEdit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarWorkManagerMaxThread('");
		this.jythonGerado.append(workManager.getName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(workManager.getMaxCount());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		for(Target targetAdd : workManager.getTargets()){
			if(targetAdd.getType().name().equals("Cluster")){
				this.jythonGerado.append("cmo.addTarget(getMBean('/Clusters/");
			} else {
				this.jythonGerado.append("cmo.addTarget(getMBean('/Servers/");
			}
			this.jythonGerado.append(targetAdd.getName());
			this.jythonGerado.append("'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
		}

		this.jythonGerado.append("activate()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("edit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("startEdit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	
		this.jythonGerado.append("CriarWorkManager('");
		this.jythonGerado.append(workManager.getName());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		for(Target targetAdd : workManager.getTargets()){
			if(targetAdd.getType().name().equals("Cluster")){
				this.jythonGerado.append("cmo.addTarget(getMBean('/Clusters/");
			} else {
				this.jythonGerado.append("cmo.addTarget(getMBean('/Servers/");
			}
			this.jythonGerado.append(targetAdd.getName());
			this.jythonGerado.append("'))");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
		}

	}

	private void createAdminServer(AdminServer adminServer) {
		this.jythonGerado.append("CriarAdminServer('");
		
		this.jythonGerado.append(adminServer.getName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(adminServer.getPort());
		this.jythonGerado.append(",");
		this.jythonGerado.append(adminServer.getSslPort());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void createMachines(Machine machine) {
		this.cd("/");
		this.create(machine.getName(), "Machine");
	}

	private void createCluster(Cluster cluster) {
		String tempTxt = "";

		if (cluster.getAddress() != null) {
			for (Address address : cluster.getAddress()) {
				tempTxt += this.createAddress(address);
				tempTxt += ",";
			}
		}

		tempTxt = tempTxt.substring(0, tempTxt.length() - 1);

		this.jythonGerado.append("CriarCluster('");		
		this.jythonGerado.append(cluster.getName());
		this.jythonGerado.append("','");
		this.jythonGerado.append(cluster.getMulticastAddress());
		this.jythonGerado.append("',");
		this.jythonGerado.append(cluster.getMulticastPort());
		this.jythonGerado.append(",'");
		this.jythonGerado.append(tempTxt);
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void createManagedServer(ManagedServer managedServer) {
		this.jythonGerado.append("CriarManagedServer('");		
		this.jythonGerado.append(managedServer.getName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(managedServer.getPort());
		this.jythonGerado.append(",'");
		this.jythonGerado.append(managedServer.getAddress());
		this.jythonGerado.append("','");
		this.jythonGerado.append(managedServer.getMachine());
		this.jythonGerado.append("','");
		this.jythonGerado.append(managedServer.getCluster());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private String createAddress(Address address) {
		return (address.getName() + ":" + address.getPort());
	}

	private void createDatasource(DataSource data) {
		this.jythonGerado.append("CriarDatasource('");		
		this.jythonGerado.append(data.getJndiName());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

		this.createConnectionPool(data.getJndiName(), data.getConnectionPool(), data.getTransaction());
		
		if (data.getTargets() != null) {
			this.jythonGerado.append("cd('/')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.jythonGerado.append("cd('SystemResources/" + data.getJndiName() +"')");
			this.jythonGerado.append(QUEBRA_DE_LINHA);
			this.setTargets(data.getTargets());
		}
	}

	private void setTargets(List<Target> targets) {
		this.jythonGerado.append("set('Targets',jarray.array([");
		if(targets != null && targets.size() > 0) {
			int tamTargets = targets.size();
			for(Target t : targets) {
				if(tamTargets==1){
					this.jythonGerado.append("ObjectName('com.bea:Name=");
					this.jythonGerado.append(t.getName());
					this.jythonGerado.append(",Type=");
					this.jythonGerado.append(t.getType());
					this.jythonGerado.append("')], ObjectName),");
				} else {
					this.jythonGerado.append("ObjectName('com.bea:Name=");
					this.jythonGerado.append(t.getName());
					this.jythonGerado.append(",Type=");
					this.jythonGerado.append(t.getType());
					this.jythonGerado.append("'),");
					tamTargets--;
				}
			}
			this.jythonGerado.replace(this.jythonGerado.length() - 1, this.jythonGerado.length(), "");
		}
		this.jythonGerado.append(")").append(QUEBRA_DE_LINHA);

	}

	private void createConnectionPool(String datasourceName, ConnectionPool connectionPool, Transaction transaction) {
		this.jythonGerado.append("CriarConnectionPool('");		
		this.jythonGerado.append(datasourceName);
		this.jythonGerado.append("','");
		this.jythonGerado.append(connectionPool.getDriverClassName());
		this.jythonGerado.append("','");
		this.jythonGerado.append(connectionPool.getUrl());
		this.jythonGerado.append("','");
		this.jythonGerado.append(connectionPool.getDbPassword());
		this.jythonGerado.append("','");
		this.jythonGerado.append(connectionPool.getDbUserName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(connectionPool.getInCapacity());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getMaxWaitingConn());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getIncrementCapacity());
		this.jythonGerado.append(",'");
		this.jythonGerado.append(connectionPool.isTestConnReserve());
		this.jythonGerado.append("','");
		this.jythonGerado.append(connectionPool.getTestTableName());
		this.jythonGerado.append("',");
		this.jythonGerado.append(connectionPool.getShrinkFrequency());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getConnCreationRetryFrequency());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getInactiveConnectionTimeoutSeconds());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getConnReserveTimeout());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getMaxCapacityInit());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getMinCapacityInit());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getStatmentCache());
		this.jythonGerado.append(",");
		this.jythonGerado.append(connectionPool.getTestFrequencySecon());
		this.jythonGerado.append(",");
		this.jythonGerado.append(transaction.isSupportsGlobalTransactions());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void createFileStore(FileStore file) {
		this.jythonGerado.append("CriarFileStore('");		
		this.jythonGerado.append(file.getName());
		this.jythonGerado.append("','");
		this.jythonGerado.append(file.getPath());
		this.jythonGerado.append("','");
		this.jythonGerado.append(file.getTarget());
		this.jythonGerado.append("','");
		this.jythonGerado.append(file.getTargetType());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
	}

	private void createJmsServer(JmsServer jmsServer) {
		this.jythonGerado.append("CriarJmsServer('");		
		this.jythonGerado.append(jmsServer.getName());
		this.jythonGerado.append("','");
		this.jythonGerado.append(jmsServer.getPersistentStore());
		this.jythonGerado.append("','");
		this.jythonGerado.append(jmsServer.getTarget());
		this.jythonGerado.append("','");
		this.jythonGerado.append(jmsServer.getTargetType());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
	}

	private void createJmsModule(JmsModule jmsModule) {
		this.jythonGerado.append("CriarJmsModule('");		
		this.jythonGerado.append(jmsModule.getName());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.cd("/");
		this.cd("JMSSystemResources/" + jmsModule.getName());	

		if (jmsModule.getTargets() != null) {
			this.setTargets(jmsModule.getTargets());
		}

		if (jmsModule.getTemplate() != null) {
			this.createTemplate(jmsModule.getName(), jmsModule.getTemplate());
		}

		if (jmsModule.getConnectionFactories() != null) {
			for (ConnectionFactory connectionFactory : jmsModule.getConnectionFactories()) {
				this.createConnectionFactory(jmsModule.getName(), connectionFactory);
			}
		}
		
		if (jmsModule.getSubdeployments() != null) {
			for (Subdeployment subdeployment : jmsModule.getSubdeployments()) {
				this.createSubdeployment(jmsModule.getName(), subdeployment);
			}
		}

		if(jmsModule.getPriorityDestinationKey() != null) {
			this.createPriorityDestinationKey(jmsModule.getName(), jmsModule.getPriorityDestinationKey());
		}

		if (jmsModule.getTopics() != null) {
			for (Topic topic : jmsModule.getTopics()) {
				this.createFileStore(topic.getFileStore());
				this.createJmsServer(topic.getSubdeployment().getJmsServers().get(0));
				this.createSubdeployment(jmsModule.getName(), topic.getSubdeployment());
				this.createTopics(jmsModule.getName(), topic);
			}
		}

		

		if("sisnetworkmodule".equals(jmsModule.getName())){
			if (jmsModule.getDistributedQueues() != null) {
				for (DistributedQueue distQueue : jmsModule.getDistributedQueues()) {
					this.createDistributedQueuesNetwork(jmsModule.getName(), distQueue);
				}
			}
			
			if (jmsModule.getQueues() != null) {
				for (Queue queue : jmsModule.getQueues()) {
					this.createQueueNetwork("QUEUE", jmsModule.getName(), queue);
				}
			}
		}

		else if("sisprovisioningmodule".equals(jmsModule.getName())){
			if (jmsModule.getDistributedQueues() != null) {
				for (DistributedQueue distQueue : jmsModule.getDistributedQueues()) {
					this.createDistributedQueuesProv(jmsModule.getName(), distQueue);
				}
			}
			
			if (jmsModule.getQueues() != null) {
				for (Queue queue : jmsModule.getQueues()) {
					this.createQueue("QUEUE", jmsModule.getName(), queue);
				}
			}
		}
		
		else if("sismodule".equals(jmsModule.getName())){
			if (jmsModule.getDistributedQueues() != null) {
				for (DistributedQueue distQueue : jmsModule.getDistributedQueues()) {
					this.createDistributedQueues(jmsModule.getName(), distQueue);
				}
			}
			
			if (jmsModule.getQueues() != null) {
				for (Queue queue : jmsModule.getQueues()) {
					this.createQueue("QUEUE", jmsModule.getName(), queue);
				}
			}
		}
	
	}

	private void createTemplate(String moduleName, Template template) {

		this.jythonGerado.append("CriarTemplate('");
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");
		this.jythonGerado.append(template.getName());
		this.jythonGerado.append("','");
		this.jythonGerado.append(template.getDestinationKeyName());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void createQueue(String type, String moduleName, Queue queue) {
		
		this.jythonGerado.append("activate()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("edit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("startEdit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName()+"DLQ");
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());
		this.jythonGerado.append("','");		
		this.jythonGerado.append("null");	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName()+"Queue");
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());	
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getPriorityDestinationKey().getName());	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

		if (queue.getDeliveryFailure() != null && queue.getDeliveryFailure().getRedeliveryDelayOverride() != 0) {
			//int redeliveryDelayOverride, int redeliveryLimit,
			this.createDeliveryFailure(moduleName, type, queue.getJndiName()+"Queue", queue.getDestinationKeys(), queue.getDeliveryFailure());
		}

	}

	private void createQueueNetwork(String type, String moduleName, Queue queue) {
		
		this.jythonGerado.append("activate()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("edit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("startEdit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName()+"_DLQ");
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());
		this.jythonGerado.append("','");		
		this.jythonGerado.append("null");	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName());
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());	
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getPriorityDestinationKey().getName());	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

		if (queue.getDeliveryFailure() != null && queue.getDeliveryFailure().getRedeliveryDelayOverride() != 0) {
			this.createDeliveryFailure(moduleName, type, queue.getJndiName(), queue.getDestinationKeys(), queue.getDeliveryFailure());
		}

	}
	
	private void createQueueProv(String type, String moduleName, Queue queue) {
		
		this.jythonGerado.append("activate()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("edit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.jythonGerado.append("startEdit()");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName()+"DLQ");
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());
		this.jythonGerado.append("','");		
		this.jythonGerado.append("null");	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		
		this.jythonGerado.append("CriarFila('");		
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getJndiName());
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getSubdeployment());	
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queue.getPriorityDestinationKey().getName());	
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

		if (queue.getDeliveryFailure() != null && queue.getDeliveryFailure().getRedeliveryDelayOverride() != 0) {
			this.createDeliveryFailure(moduleName, type, queue.getJndiName(), queue.getDestinationKeys(), queue.getDeliveryFailure());
		}

	}
	
	private void createDeliveryFailure(String moduleName, String type, String queueName, String destinationKey, DeliveryFailure deliveryFailure) {
		this.jythonGerado.append("CriarDeliveryfailure('");
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");
		this.jythonGerado.append(type);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(queueName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(destinationKey);	
		this.jythonGerado.append("',");
		this.jythonGerado.append(deliveryFailure.getRedeliveryDelayOverride());
		this.jythonGerado.append(",");
		this.jythonGerado.append(deliveryFailure.getRedeliveryLimit());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	private void createSubdeployment(String moduleName, Subdeployment subdeployment) {
		this.jythonGerado.append("CriarSubDeployment('");	
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(subdeployment.getName());
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		this.setTargets(subdeployment.getTargets());
	}
	
	private void createDistributedQueues(String moduleName, DistributedQueue distributeQueue) {
		this.createQueue("UniformDistributedQueue", moduleName, distributeQueue);

	}
	
	private void createDistributedQueuesNetwork(String moduleName, DistributedQueue distributeQueue) {
		this.createQueueNetwork("UniformDistributedQueue", moduleName, distributeQueue);

	}
	
	private void createDistributedQueuesProv(String moduleName, DistributedQueue distributeQueue) {
		this.createQueueProv("UniformDistributedQueue", moduleName, distributeQueue);

	}
	
	private void createTopics(String moduleName, Topic topic) {
		this.jythonGerado.append("CriarTopic('");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(topic.getName());
		this.jythonGerado.append("','");		
		this.jythonGerado.append(topic.getSubdeployment().getName());		
		this.jythonGerado.append("',");		
		this.jythonGerado.append(topic.getMessagingPerformancePreference());		
		this.jythonGerado.append(",");		
		this.jythonGerado.append(topic.getRedeliveryLimit());			
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

	}

	private void createPriorityDestinationKey(String moduleName, PriorityDestinationKey priorityDestinationKey) {
		this.jythonGerado.append("CriarPriorityDestinationKey('");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(priorityDestinationKey.getName());
		this.jythonGerado.append("','");		
		this.jythonGerado.append(priorityDestinationKey.getSortKey());		
		this.jythonGerado.append("','");		
		this.jythonGerado.append(priorityDestinationKey.getKeyType());		
		this.jythonGerado.append("','");		
		this.jythonGerado.append(priorityDestinationKey.getDirection());			
		this.jythonGerado.append("')");
		this.jythonGerado.append(QUEBRA_DE_LINHA);

	}

	private void createConnectionFactory(String moduleName, ConnectionFactory connectionFactory) {
		this.jythonGerado.append("CriarConnectionfactory('");		
		this.jythonGerado.append(moduleName);
		this.jythonGerado.append("','");		
		this.jythonGerado.append(connectionFactory.getName());		
		this.jythonGerado.append("','");		
		this.jythonGerado.append(connectionFactory.getJndiName());		
		this.jythonGerado.append("','");		
		this.jythonGerado.append(connectionFactory.getReconnectPolicy());		
		this.jythonGerado.append("',");		
		this.jythonGerado.append(connectionFactory.getMaxMessagesPerSession());		
		this.jythonGerado.append(",'");		
		this.jythonGerado.append(connectionFactory.isServerAffinityEnabled());		
		this.jythonGerado.append("','");		
		this.jythonGerado.append(connectionFactory.isXaConnectionFactoryEnabled());		
		this.jythonGerado.append("',");		
		this.jythonGerado.append(connectionFactory.getSendTimeout());
		this.jythonGerado.append(")");
		this.jythonGerado.append(QUEBRA_DE_LINHA);
	}

	/*
	 * Inicio dos comandos nativos do WSLT (Privados)
	 */
	private void connect(String user, String password, String host) {
		generateJython("connect('" + user + "', '" + password + "', '" + host + "')");
	}

	private void cd(String path) {
		generateJython("cd('" + path + "')");
	}

	private void edit() {
		generateJython("edit()");
	}

	private void startEdit() {
		generateJython("startEdit()");
	}

	private void save() {
		generateJython("save()");
	}

	private void activate(String var, String value) {
		String options = "";
		if(var != null && value != null) {
			options = var + "='" + value +"'";
		}
		generateJython("activate(" + options + ")");
	}

	private void updateDomain() {
		generateJython("updateDomain()");
	}

	private String state(String serverName) {
		return generateJython("state('" + serverName + "')");
	}

	private String state(String serverName, String type) {
		return generateJython("state('" + serverName + "', '" + type + "')");
	}

	private String ls(String dir) {
		return generateJython("ls('" + dir + "')");
	}

	private String set(String param, String value) {
		return generateJython("set('" + param + "','" + value + "')");
	}

	private String set(String param, int value) {
		return generateJython("set('" + param + "'," + String.valueOf(value) + ")");
	}

	private String set(String param, long value) {
		return generateJython("set('" + param + "'," + String.valueOf(value) + ")");
	}

	private String set(String param, boolean value) {
		return generateJython("set('" + param + "'," + String.valueOf(value) + ")");
	}

	private String create(String varName, String name, String childMBeanType) {
		// name, childMBeanType, [baseProviderType]
		return generateJython(varName + "=create('" + name + "','" + childMBeanType + "')");
	}

	private String create(String name, String childMBeanType) {
		// name, childMBeanType, [baseProviderType]
		return generateJython("create('" + name + "','" + childMBeanType + "')");
	}

	private String dumpStack() {
		return generateJython("dumpStack()");
	}

	private String assign(String sourceType, String sourceName, String destinationType, String destinationName) {
		return generateJython("assign('" + sourceType + "','" + sourceName + "','" + destinationType + "','" + destinationName + "')");
	}

	private String closeTemplate() {
		return generateJython("closeTemplate()");
	}

	private String writeTemplate(String templateName) {
		return generateJython("writeTemplate('" + templateName + ")'");
	}

	private String readTemplate(String templateName) {
		return generateJython("readTemplate('" + templateName + "')");
	}

	private String readDomain(String domainName) {
		return generateJython("readDomain('" + domainName + ")'");
	}

	private String genericCommandUsingVariable(String variable, String command, String... args) {
		StringBuffer realArgs = new StringBuffer();
		String temp = "";

		realArgs.append("(");

		for (String arg : args) {
			temp += "'";
			temp += arg;
			temp += "'";
			temp += ",";
		}

		temp = temp.substring(0, temp.length() - 1);
		realArgs.append(temp);
		realArgs.append(")");

		return generateJython(variable + "." + command + (realArgs.toString()));
	}

	private String genericCommandIntoVariable(String variable, String command, String... args) {
		StringBuffer realArgs = new StringBuffer();
		String temp = "";

		realArgs.append("(");

		for (String arg : args) {
			temp += "'";
			temp += arg;
			temp += "'";
			temp += ",";
		}

		temp = temp.substring(0, temp.length() - 1);
		realArgs.append(temp);
		realArgs.append(")");

		return generateJython(variable + "=" + command + (realArgs.toString()));
	}

	private String generateJython(String command) {
		String retorno = "";

		this.jythonGerado.append(command);
		this.jythonGerado.append(QUEBRA_DE_LINHA);
		return retorno;

	}
	
	public String setWeblogicPassword(String varName, String password) {
		return generateJython(varName + ".setPassword('" + password + "')");
	}

	public String setCmoValue(String value) {
		return generateJython("cmo.setValue('" + value + "')");
	}
}
