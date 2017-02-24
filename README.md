# MultiThreadsDemo
 + 调用`thread().interrupt()`是中断一个线程。被中断的线程运行时如果在执行可中断的阻塞方法时，都会检测该线程是否被中断了，如果中断了则抛出`InterruptedException`，从而停止当前阻塞方法。捕获该异常以后，如果希望停止该线程应该这样做：
```java
Thread.currentThread().interrupt(); // Here!
throw new RuntimeException(ex);
```  
如果没有将异常封装成`RuntimeException`，该线程是不会停止的
 + 如果初始化`ThreadLocal`变量使用如下方式，则对所有线程该变量都是可见的：
 ```java
 private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
                                                     /**
                                                      * 这种方法初始化ThreadLocal变量对所有线程都可见
                                                      */
                                                     @Override
                                                     protected Integer initialValue() {
                                                         return 99;
                                                     }

                                                 };
 ```
  而使用`set`方式设置`ThreadLocal`变量的值，则只对当前线程可见
  + `volatile`的作用不是使变量具有原子性，而是使变量具有可见性，即变量被某一线程修改后，其它线程可以立刻感知到
  + `join`方法是等待当前线程执行完
  + `yield`方法不是暂停当前线程，而是让出资源使用权，如果此时没有其他线程，则该线程继续执行
  + `AtomicInteger`变量具有原子性，所谓原子性是指对该变量的操作要么成功，要么失败，且在线程安全，即同一时间只允许一个线程操作该变量
  + `synchronized`的使用：
  ```java
  public synchronized void method() { // blocks "this" from here.... 
    ...
  } // to here

  public void method() { 
      synchronized( this ) { // blocks "this" from here .... 
          ....
      }  // to here...
  }
  ```
  以上两种方式实际是没有区别的。使用这种方式加锁，将锁住整个对象，线程A获取锁以后，线程B将无法访问这个对象的任何被`synchronized`修饰的方法，但可以访问其它非同步方法，即没有被`synchronized`修饰的方法。但是下面这种加锁方式比上面的方式效率高一倍左右：
  + 使用其它对象锁
  ```java
  // Using specific locks
  Object inputLock = new Object();

  private void someInputRelatedWork() {
      synchronize(inputLock) { 
          ... 
      } 
  }
  ```
  + 一个线程池中，既可以多个线程同时执行一个任务，也可以多个线程执行多个不同的任务
  + `awaitTermination`是让主线程等待线程池的任务执行，如果等待的过程中发生了请求关闭、超时或者当前线程中断，则主线程继续往下执行，而线程池中的任务继续执行，直到任务结束
  + **可重入锁**指的是在一个线程中可以多次获取同一把锁，比如：一个线程在执行一个带锁的方法，该方法又调用了另一个需要向同锁的方法，该线程可以直接执行调用的方法，而不需要重新获得锁。可重入锁的最大作用是**避免死锁**。`ReentrantLock`和`synchronized`都是可重入锁
  + `semaphore.release(n)`如果一个线程没有获取任何permit，则调用该方法将增加n个permits。如果一个线程申请了x个permit，但释放了y个（y>x），将增加y-x个permits
  + `ExecutorService.invokeAll`将提交一组任务，返回时间取决于任务中最长的那个
  + `ExecutorService.invokeAny`将提交一组任务，那个任务先完成（未抛出异常）则返回其结果，一旦正常或异常返回后，则取消尚未完成的任务
  + 在Java中，线程不允许抛出`checked exception`，也就是说各个线程自己要把自己的`checked exception`处理掉。例如，在`Runnable.run()`签名中没有申明`throws Exception`。但是线程依然可能抛出`unchecked exception`，当线程抛出此类异常时线程就会终结，而对于主线程和其它线程完全不受影响，且完全感知不到某个线程抛出的异常（也就是说无法catch到这个异常）。JVM的这种设计源自一种理念：线程是独立执行的代码片段，线程的问题应该由线程自己来解决，而不委托到外部。基于这种理念，线程方法的异常（无论是checked 还是unchecked exception），都应该在线程代码边界（run方法内）进行try catch并处理掉。换句话说，我们不能捕获从线程中逃逸的异常。
  + 如果需要捕获线程的unchecked exception应该怎么办？可以自定义类实现`Thread.UncaughtExceptionHandler`接口，Thread允许我们在每一个Thread对象上添加一个异常处理器`UncaughtExceptionHandler`，通过`thread.setUncaughtExceptionHandler`方法设置。
  + 而在线程池中比较特殊。默认情况下，线程池java.util.concurrent.ThreadPoolExecutor会catch住所有异常，如果使用submit(Runnable)或使用submit(Callable)获取其结果(java.util.concurrent.Future.get())会抛出ExecutionException，该异常即是Runnable或者Callable抛出的异常。也就是说，当我们向线程池提交了任务时，如果不理会任务结果（Future.get()），那么此异常将被线程吞掉。
  + 如果需要获取线程池在运行时抛出的异常，有三种方法：
    1. 通过Future.get获取
    2. 通过继承并覆盖ThreadPoolExecutor.afterExecute(Runnable r, Throwable t)获取
    3. 通过实现ThreadFactory接口，覆盖newThread方法，并在该方法中通过setUncaughtExceptionHandler设置自定义的Handler
  + 通常可用以下方式来提升程序可伸缩性
    1. 减少锁的持有时间
    2. 降低锁的粒度。例如将锁分段
    3. 采用非独占的锁或非阻塞锁来代替独占锁。例如使用原子变量
