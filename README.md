# MarsRealEstates
This Android app extends that of Google code-lab "Mars Photos" and Udacity's Mars Real Estates with a little different.
In this app:
1. Present Mars plots overview (OverviewFragment) and Mars plot in details (DetailFragment). Data can be filter by TYPE of plots to show
2. Use activity [ViewModel] sharing in 2 fragments (different from Udacity's, which uses 2 ViewModels for 2 fragments)
3. Use Navigation Args to tranfer data when navigating from OverViewFragment to DetailFragment (different form Udacity's, which uses Parceble)
4. Don't use BindingAdapter for these LiveData: [List<MarsPhoto>] and [Status] (use LifeCyccle Observer)
