# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :recipe_ingredient do
    recipe_id 1
    ingredient_id 1
    amount 1
  end
end
