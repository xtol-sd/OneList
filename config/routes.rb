Onelist::Application.routes.draw do
 get "lists/add_items" => "lists#add_items", as: "add_items"

 root :to => "lists#index"
  devise_for :users, :controllers => {:registrations => "registrations"}
  resources :users
  resources :recipes, :items, :lists, :menus
end