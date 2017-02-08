# MultiThreadsDemo
 + 调用`thread().interrupt()`是中断一个线程。被中断的线程运行时如果在执行可中断的阻塞方法时，都会检测该线程是否被中断了，如果中断了则抛出`InterruptedException`，从而停止当前阻塞方法。捕获该异常以后，如果希望停止该线程应该这样做：
```java
Thread.currentThread().interrupt(); // Here!
throw new RuntimeException(ex);
```  
如果没有将异常封装成`RuntimeException`，该线程是不会停止的
 + 如果初始化ThreadLocal变量使用如下方式，则对所有线程该变量都是可见的：
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
    ...
    ...
} // to here

public void method() { 
    synchronized( this ) { // blocks "this" from here .... 
        ....
        ....
        ....
    }  // to here...
}
  ```
  以上两种方式实际是没有区别的。使用这种方式加锁，将锁住整个对象，线程A获取锁以后，线程B将无法访问这个对象的任何被`synchronized`修饰的方法，但可以访问其它非同步方法，即没有被`synchronized`修饰的方法。但是下面这种加锁方式比上面的方式效率高一倍左右：
  ```java
// Using specific locks
Object inputLock = new Object();

private void someInputRelatedWork() {
    synchronize(inputLock) { 
        ... 
    } 
}
  ```
  
