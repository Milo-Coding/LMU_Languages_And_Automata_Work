package main

import (
	"log"
	"math/rand"
	"time"
	"sync"
	"sync/atomic"
)

// A little utility that simulates performing a task for a random duration.
// For example, calling do(10, "Remy", "is cooking") will compute a random
// number of milliseconds between 5000 and 10000, log "Remy is cooking",
// and sleep the current goroutine for that much time.

func do(seconds int, action ...any) {
    log.Println(action...)
    randomMillis := 500 * seconds + rand.Intn(500 * seconds)
    time.Sleep(time.Duration(randomMillis) * time.Millisecond)
}

type Order struct {
	id uint64
	customer string
	// reply which is a channel containing another channel (need a pointer)
	cook string
}
var nextId atomic.Uint64

// A waiter can only hold 3 orders at once
var Waiter = make(chan *Order, 3)

func Cook(name string) {
	log.Println(name, "is cooking")
	// loop forever
		// wait for an order from waiter
		// cook it
		// put name of cook in order
		// send it vack into reply channel: order.reply <- order
}

func Customer(name string, wg *sync.WaitGroup) {
	for mealsEaten := 0; mealsEaten <5; {
		// place an order
		// select statement so that if the waiter gets it within 7 seconds
			// then you get it from the cookd and eate it (mealsEaten++)
			// If you don't get it, leave the restarant
			// do(5, name, "is waiting too long, abandoning order", order.id)
		select {
		//case Waiter <- &Order(id: nextId.Inc(), customer: name):
		} 
	}
}

func main() {
	customers := [10]string{
		"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai",
	}

	// start each customer
	var wg sync.WaitGroup
	for _, customer := range customers {
		wg.Add(1)
		go Customer(customer, &wg)
	}

	// start each cook
	go Cook("Remmy")
	go Cook("Linguini")
	go Cook("Colette")

	// wait for all customers to finish

	log.Println("Restaurant closing")
}