# Tripacker development progress: 
- Profile 
	User could login and sign up from landing page;
	User could manage the profile;
- Spot
	User could add a new spot;
	User could check one spot with detailed information;
	User could check all the spots in one city;
- Trip 
	Front-end xml has implemented; Need to integrate with APIs.
- Explore 
	Front-end xml has implemented; Need to integrate with APIs.

# Use Case
1 Join Community - Finished
2 Manage User Account - Finished
3 Create Spot - 80%
4 Edit Spot -  70 %
5 Show Spot - 60% 
7 Create/Submit Trip - 40%
8 Share Trip / Collaboration - 30%
9 Check customized trip - 30%

# Packages
--controller
..----ActivityComponent
..----CreateSpot
..----EditSpot
--DBLayout
..----DatabaseHelper
--entity
..----SpotEntity
..----TripEntity
..----UserEntity
--exception
..----InvalidDataException
..----NetworkConnectionException
..----UserNotFoundException
--navigation
..----SlidingTabLayout
..----SlidingTabStrip
--presenter
..----Presenter
..----SpotListPresenter
..----UserDetailsPresenter
--view
..----activity
....----EditProfileActivity
....----LoginActivity
....----MainActivity
....----SignupActivity
....----SpotEditActivity
....----SpotViewActivity
..----adapter
....----SpotsTimelineAdapter
....----TripsTimelineAdapter
....----TripRecyclerViewAdapter
..----fragment
....----ExploreFragment
....----FavoriteFragment
....----PageFragment
....----ProfilePageFragment
....----SpotFragment
....----TripFragment
..----holder
....----TripViewHolders
--ws
..----local
..----remote
....----APIConnection
....----AsyncCaller
....----AsyncJsonGetTask
....----AsyncJsonPostTask
....----AsyncJsonPutTask
....----AsyncStarting
....----TripPackerAPIs
....----WebServices


