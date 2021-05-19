package com.guowei.colorsapp.di.presentation

import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class, ViewModelModule::class])
interface PresentationComponent {
//    fun inject(fragment: QuestionsListFragment)
//    fun inject(activity: QuestionDetailsActivity)
//    fun inject(questionsListActivity: QuestionsListActivity)
//    fun inject(viewModelActivity: ViewModelActivity)
}