# Schedule

## Goal
* To test the @Scheduled support
* Identify some traps in the schedule support 


## Enable Schedule
1. Only add @EnableScheduling can make @Scheduled works (ScheduleSingleThreadApplication )

  > @Schedule is single thread for all the jobs if you only set @EnableSchedule   

2. Add @EnableAsync and @Async on the schedule method

  > Progress may be limited by the default shared thread pool
  
3. Create your own thread pool and use it by @Async("{threadPoolName})

  > This is more proper way to handle Spring Schedule Task
  
## Tips

* @Async can be applied to class level to make all the underlying method becomes async executed
* If you have a @Bean as ExecutorService, then you must specify the value of @Async.
