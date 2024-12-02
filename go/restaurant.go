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

// An order for a meal is placed by a customer and is taken by a cook.
// When the meal is finished, the cook will send the finished meal through
// the reply channel. Each order has a unique id, safely incremented using
// an atomic counter.
type Order struct {
	id uint64
	customer string
	reply chan *Order
	cook string
}
var nextId uint64

// The waiter is represented by a channel of orders. The waiter will
// take orders from customers and send them to the cook. The cook will
// then send the prepared meal back to the waiter. To simulate a waiter
// being busy, the waiter channel has a buffer capacity of 3 orders.
var Waiter = make(chan *Order, 3)

// A cook spends their time fetching orders from the order channel,
// cooking the requested meal, and sending the meal back through the
// order's reply channel.
func Cook(name string) {
	log.Println(name, "started working")
	for {
		select {
		case order := <- Waiter: // Fetch an order from the Waiter channel
			do(10, name, "is cooking order", order.id, "for", order.customer)
			order.cook = name // Set the cook's name in the order
			order.reply <- order // Send the cooked order back
		}
	}
}

// A customer eats five meals and then goes home. Each time they enter the
// restaurant, they place an order with the waiter. If the waiter is too
// busy, the customer will wait for 5 seconds before abandoning the order.
// If the order does get placed, then they will wait as long as necessary
// for the meal to be cooked and delivered.
func Customer(name string, wg *sync.WaitGroup) {
	defer wg.Done()

	log.Println(name, "is hungry")
	mealsEaten := 0

	for mealsEaten < 5 {
		log.Println(name, "placed an order")
		reply := make(chan *Order) // Create a reply channel for the order

		select {
		case Waiter <- &Order{id: atomic.AddUint64(&nextId, 1), customer: name, reply: reply}:
			// Order placed successfully, wait for meal
			order := <-reply // Wait for the meal to be delivered
			do(5, name, "is eating order", order.id, "prepared by", order.cook)
			mealsEaten++
		case <-time.After(5 * time.Second):
			do(5, name, "is waiting too long, abandoning order") //order.id
		}
	}
}

func main() {
	rand.Seed(time.Now().UnixNano()) // Seed random number generator

	customers := [10]string{
		"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai",
	}

	// Start each cook as a separate goroutine
	go Cook("Remy")
	go Cook("Linguini")
	go Cook("Colette")

	// Start each customer as a separate goroutine
	var wg sync.WaitGroup
	for _, customer := range customers {
		wg.Add(1)
		go Customer(customer, &wg)
	}

	// Wait for all customers to finish
	wg.Wait()

	log.Println("Restaurant closing")
}