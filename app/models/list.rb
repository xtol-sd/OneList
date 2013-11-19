class List < ActiveRecord::Base
  has_many :list_items
  has_many :items, :through => :list_items
  accepts_nested_attributes_for :list_items
end
