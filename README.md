#Qookie

When you code a mobile application that needs to transfer data to a backend you generally have two options: a *small cookie* model and a *big cookie* model.


## Big Cookie

![big cookie](https://raw.github.com/mmarcon/qookie/master/docs/big_cookie.png)

Transfer as much data as possible, as infrequently as possible.

## Small Cookie

![small cookie](https://raw.github.com/mmarcon/qookie/master/docs/small_cookie.png)
![small cookie](https://raw.github.com/mmarcon/qookie/master/docs/small_cookie.png)
![small cookie](https://raw.github.com/mmarcon/qookie/master/docs/small_cookie.png)
![small cookie](https://raw.github.com/mmarcon/qookie/master/docs/small_cookie.png)

Transfer as little data as possible, and do transfers very frequently.

## What's best?

> **Big Cookie**. Little Cookie heavily fragments radio use. For every data transfer the radio stays on for 5 seconds at full power followed by 10-60 seconds at a lower power state before returning to Standby. Every time you transfer data you are powering the radio for at least 20 seconds. So sending small amounts of data frequently is the best way to drain a battery. Let’s say you send analytics data every 15 seconds and the the user clicks on a link intermittently. The result will be the radio is on continuously. So don’t do that [1].

In order to support this Big Cookie model you'll need a queue that gets filled with data up to a certain point and once the threshold is reached it sends *the big cookie*.

This is exactly what **Qookie** is about. **Qookie** is an abstraction of a queue that can be limited in size (number of items and bytes) and automatically flushes once that size is reached.


####Credits

 * [1] [This article](http://highscalability.com/blog/2013/9/18/if-youre-programming-a-cell-phone-like-a-server-youre-doing.html) is awesome. It explains in details many of the challenges developers face when building mobile applications and lists the things one should always keep in mind.
 * [Cookie](http://thenounproject.com/noun/cookie/#icon-No17125) designed by [Caroline Lancaster](http://thenounproject.com/car.lancaster) from The Noun Project.