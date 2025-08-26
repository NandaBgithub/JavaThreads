#

## The difference between process and thread?

A process is a program or set of instructions (code) that is loaded into the memory from disk, and scheduled by the operating system to execute by the CPU.
Processes are either running, pending or terminated and is managed by the operating system.
Processes contain sets of instructions for the CPU to run, and it is designated some hardware resources
like disk memory, CPU registers, RAM, and IO.
A process contains a thread.
The thread is a abstraction of a unit of execution within a processes. A thread has its own stack and thread specific data,
but threads have to occupy the memory space of a process. Thus, whilst processes cannot inerfere with other process' data due to processes having independent memory space to other processes,
threads can interfere with other thread's data, for they share the same memory space within the process.

## Race conditions

A race condition is a concurrency problem that can occur inside a section of code that is executed by multiple threads and where the sequence of execution for the threads makes a difference in the result of the concurrent execution of the section of code. This section is "the critical section".

In the case where multiple threads are executing a critical section, resulting in different results for each thread, this critical section is then said to have a "race condition".

Formally,

- Race condition is the phenomena when multiple threads compete for the same resources, where the order of access of such resources is important.

- Critical sections are areas of code that lead to such race conditions.

Read-modify-write race condition

- Primitive case: two or more threads given variable, then modify it, then write back to it.

Check-then-act race condition

- Two or more threads check a given condition, and depending on the result of such condition, attempt to change the value that the conditional relies on.

## Thread safety and shared resources

What does it mean for something to be considered thread safe?

1. When there is no race condition that results in multiple threads accessing some shared resource.
2. When there is no other thread that controls the resource, in otherwords no concurrency at all, then by default, it is thread safe.

What are resources that are not shared?

- Local primitive variables are thread safe, they are stored in the thread's stack and not shared between threads.
- Local object references, whereby the references are stored uniquely in each thread's stack, but the value they reference are inside a heap shared between threads.
- Objects created locally that is never passed to other threads are thread safe, unless, the object is passed and is stored in a way that allows other threads to access it.

But bruh bruh, is there a general rule of thumb to know whether my method is going to be thread safe?

Yes, for the second definition of thread safe, one can use...
"The thread control and escape rule" (TCER)
States that if a resource or resources (object, array, files, sockets, database connections) are created, used and disposed (or reference is nulled since java is garbage collected) within the control
of the same thread, and never escapes the control of this thread, the use of the resource is thread safe.

