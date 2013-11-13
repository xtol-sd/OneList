Onelist::Application.routes.draw do
  get "ingredients/index"
  get "ingredients/new"
  get "ingredients/edit"
  get "recipes/index"
  get "recipes/new"
  get "recipes/edit"
  root :to => "home#index"
  devise_for :users, :controllers => {:registrations => "registrations"}
  resources :users
end