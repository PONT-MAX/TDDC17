;;This is a small problem instance for the standard Logistics domain,
;; as defined in "logistic.pddl".

(define (problem C3_2)
  (:domain logistics)
  (:objects
   city1 city2 city3          ;; there are three cities,
   truck1 truck2 truck3       ;; one truck in each city,
   bigtruck1 bigtruck2 bigtruck3 ;; one bigtruck in each city,
   airplane1 airplane2 airplane3 ;; only one airplane,
   train1		      ;; only one train,
   office1 office2 office3    ;; offices are "non-airport" locations
   airport1 airport2 	      ;; airports
   station1 station3	      ;; train stations
   packet1 packet2 packet3 packet4 packet5 ;; two packages to be delivered
   bigpacket1 bigpacket2 bigpacket3 bigpacket4 bigpacket5  ;; two big packages to be delivered
   )
  (:init
   ;; Type declarations:
   (object packet1) (object packet2) (object packet3) (object packet4) (object packet5) 
   (bigobject bigpacket1) (bigobject bigpacket2) (bigobject bigpacket3) (bigobject bigpacket4) (bigobject bigpacket5)

   ;; all vehicles must be declared as both "vehicle" and their
   ;; appropriate subtype,
   (vehicle truck1) (vehicle truck2) (vehicle truck3)
   (vehicle bigtruck1) (vehicle bigtruck2) (vehicle bigtruck3)
   (vehicle airplane1) (vehicle airplane2) (vehicle airplane3) 
   (vehicle train1)
   (truck truck1) (truck truck2) (truck truck3)
   (bigtruck bigtruck1) (bigtruck bigtruck2) (bigtruck bigtruck3)
   (airplane airplane1) (train train1)

   ;; likewise, airports must be declared both as "location" and as
   ;; the subtype "airport",
   (location office1) (location office2) (location office3)
   (location airport1) (location airport2)
   (airport airport1) (airport airport2) 
   (location station1) (location station3)
   (station station1) (station station3) 
   (city city1) (city city2) (city city3)

   ;; "loc" defines the topology of the problem,
   (loc office1 city1) (loc airport1 city1) (loc station1 city1)
   (loc office2 city2) (loc airport2 city2)
   (loc office3 city3) (loc station3 city3)

   ;; The actual initial state of the problem, which specifies the
   ;; initial locations of all packages and all vehicles:
   (at packet1 office1)
   (at packet2 office2)
   (at packet3 office3)
   (at packet4 office1)
   (at packet5 office2)
   (at bigpacket1 office1)
   (at bigpacket2 office2)
   (at bigpacket3 office3)
   (at bigpacket4 office1)
   (at bigpacket5 office2)
   (at truck1 airport1)
   (at truck2 airport2)
   (at truck3 station3)
   (at bigtruck1 airport1)
   (at bigtruck2 airport2)
   (at bigtruck3 station3)
   (at airplane1 airport1)
   (at airplane2 airport2)
   (at airplane3 airport1)
   (at train1 station1)
   )

  ;; The goal is to have both packages delivered to their destinations:
  (:goal (and (at packet1 office3) (at packet2 office1) (at packet3 office1)
  	      (at packet4 office2) (at packet5 office3)
  	      (at bigpacket1 office3) (at bigpacket2 office1)
	      (at bigpacket3 office1) (at bigpacket4 office2) 
	      (at bigpacket5 office3)))
  )
