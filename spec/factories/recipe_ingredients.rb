# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :recipe_ingredient do
    recipe_id ""
    ingredient_id ""
    amount ""
  end
end
