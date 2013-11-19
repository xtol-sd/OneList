class User < ActiveRecord::Base
  has_many :recipes
  has_many :lists

  #The below associations are NOT final, placeholders
  #has_many :items, :through => :recipes
  #has_many :items, :through => :lists

  rolify
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  
end
