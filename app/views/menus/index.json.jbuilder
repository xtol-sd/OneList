json.array!(@menus) do |menu|
  json.extract! menu, :name, :comment, :recipe_id, :list_id
  json.url menu_url(menu, format: :json)
end
