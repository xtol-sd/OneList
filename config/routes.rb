Onelist::Application.routes.draw do

  get "lists/:id/add_others" => "lists#add_others", as: "add_others"
  authenticated :user do
    root :to => "lists#index", as: :authenticated_root
  end

  devise_for :users, :controllers => {:registrations => "registrations"}

  devise_scope :user do
    root to: "home#index"
  end

  resources :users
  resources :recipes, :items, :lists, :menus, :others
end