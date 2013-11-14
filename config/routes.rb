Onelist::Application.routes.draw do
  root :to => "recipes#index"
  devise_for :users, :controllers => {:registrations => "registrations"}
  resources :users
  resources :recipes, :ingredients
end