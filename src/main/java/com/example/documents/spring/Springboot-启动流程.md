# 创建`SpringApplication`对象
- 指定bean的来源
- 创建并初始化`ApplicationContextInitializer`
- 创建并初始化`ApplicationListener`
```java
	public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, "PrimarySources must not be null");
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        this.bootstrapRegistryInitializers = new ArrayList<>(
        getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
        setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = deduceMainApplicationClass();
        }  
```
# `run`
- 创建并初始化`SpringApplicationRunListeners`，调用`staring`方法发布开始事件
- 创建并初始化`ApplicationArguments`接收args参数
- 创建并初始化`ConfigurableEnvironment`，发布`environmentPrepared`事件
- 打印banner
- 构建`ApplicationContext`对象，填充环境等配置信息，发布事件
- 调用`refreshContext`，刷新上下文，开始spring的启动
- 结束刷新
- 打印启动时间
- 发布结束事件
- `ApplicationRunner`和`CommandLineRunner`
- 发布`ready`事件
```java
public ConfigurableApplicationContext run(String... args) {
		long startTime = System.nanoTime(); // 开始时间
		DefaultBootstrapContext bootstrapContext = createBootstrapContext();
		ConfigurableApplicationContext context = null;
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting(bootstrapContext, this.mainApplicationClass); //开始事件
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args); // 接收参数
			ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);// 准备环境
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment); // 打印banner
			context = createApplicationContext(); // 创建applicationContext
			context.setApplicationStartup(this.applicationStartup);
			prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner); //准备上下文
			refreshContext(context); // 刷新上下文
			afterRefresh(context, applicationArguments); // 结束刷新
			Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime); // 启动时间
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), timeTakenToStartup);
			}
			listeners.started(context, timeTakenToStartup); // 已启动事件
			callRunners(context, applicationArguments); 
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, listeners);
			throw new IllegalStateException(ex);
		}
		try {
			Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime);
			listeners.ready(context, timeTakenToReady); // 就绪事件
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
```