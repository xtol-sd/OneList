Onelist::Application.routes.draw do

  get "lists/:id/add_items" => "lists#add_items", as: "add_items"
  authenticated :user do
    root :to => "lists#index", as: :authenticated_root
  end

  devise_for :users, :controllers => {:registrations => "registrations"}

  devise_scope :user do
    root to: "home#index"
  end

  resources :users
  resources :recipes, :items, :lists, :menus
end