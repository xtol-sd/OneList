Onelist::Application.routes.draw do
  
 root :to => "lists#index"
  devise_for :users, :controllers => {:registrations => "registrations"}
  resources :users
  resources :recipes, :items, :lists
end