class ListItem < ActiveRecord::Base
  belongs_to :list
  belongs_to :item
  accepts_nested_attributes_for :item
end

