# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :menu do
    name "MyString"
    comment "MyString"
    recipe_id 1
    list_id 1
  end
end
