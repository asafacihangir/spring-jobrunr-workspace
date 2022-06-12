# JobRunr

The ultimate library to perform background processing on the JVM.<br/>
Dead simple API. Extensible. Reliable. <br/>
Distributed and backed by persistent storage. <br/>
Open and free for commercial use.
</p>  
<br/>



View more feedback on [jobrunr.io](https://www.jobrunr.io/en/#why-jobrunr).




## Features
- Simple: just use Java 8 lambda's to create a background job.
- Distributed & cluster-friendly: guarantees execution by single scheduler instance using optimistic locking.
- Persistent jobs: using either a RDMBS (four tables and a view) or a noSQL data store.
- Embeddable: built to be embedded in existing applications.
- Minimal dependencies: ([ASM](https://asm.ow2.io/), slf4j and either [jackson](https://github.com/FasterXML/jackson) and jackson-datatype-jsr310, [gson](https://github.com/google/gson) or a JSON-B compliant library).

## Usage scenarios
Some scenarios where it may be a good fit:
- within a REST api return response to client immediately and perform long-running job in the background
- mass notifications/newsletters
- calculations of wages and the creation of the resulting documents
- batch import from xml, csv or json
- creation of archives
- firing off web hooks
- image/video processing
- purging temporary files
- recurring automated reports
- database maintenance
- updating elasticsearch/solr after data changes
- *…and so on*

You can start small and process jobs within your web app or scale horizontally and add as many background job servers as you want to handle a peak of jobs. JobRunr will distribute the load over all the servers for you. JobRunr is also fault-tolerant - is an external web service down? No worries, the job is automatically retried 10-times with a smart back-off policy.

JobRunr is a Java alternative to [HangFire](https://github.com/HangfireIO/Hangfire), [Resque](https://github.com/resque/resque), [Sidekiq](http://sidekiq.org), [delayed_job](https://github.com/collectiveidea/delayed_job), [Celery](https://github.com/celery/celery) and is similar to [Quartz](https://github.com/quartz-scheduler/quartz) and [Spring Task Scheduler](https://github.com/spring-guides/gs-scheduling-tasks).

