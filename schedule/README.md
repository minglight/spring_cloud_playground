# Schedule

## Goal
* To test the @Scheduled support
* Identify some traps in the schedule support 


## Enable Schedule
1. Only add @EnableScheduling can make @Scheduled works (ScheduleSingleThreadApplication )

  > @Schedule is single tread for all the jobs if you only set @EnableSchedule   

2. Add @EnableAsync and @Async on the schedule method

  > Progress may be limited by the default shared thread pool
  
3. Create your own thread pool and use it by @Async("{threadPoolName})

  > This is more proper way to handle Spring Schedule Task