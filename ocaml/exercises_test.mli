val passes : int ref
val failures : int ref
val suite : string -> unit
val expect : bool -> unit
val expect_equal_lists : 'a list -> 'a list -> unit
