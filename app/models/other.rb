class Other < ActiveRecord::Base
  has_many :list_others, :dependent => :destroy
  has_many :lists, :through => :list_others
end
