# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create( [ { name: 'Chicago' },  { name: 'Copenhagen' } ],)
#   Mayor.create(name: 'Emanuel',  city: cities.first)
# Environment variables (ENV [ '...' ],) can be set in the file config/application.yml.
# See http://railsapps.github.io/rails-environment-variables.html
# puts 'ROLES'
# YAML.load(ENV['ROLES']).each do |role|
#   Role.find_or_create_by_name(role)
#   puts 'role: ' << role
# end
# puts 'DEFAULT USERS'
# user = User.find_or_create_by_email :name => ENV['ADMIN_NAME'].dup, :email => ENV['ADMIN_EMAIL'].dup,  :password => ENV['ADMIN_PASSWORD'].dup,  :password_confirmation => ENV['ADMIN_PASSWORD'].dup
# puts 'user: ' << user.name
# user.add_role :admin

other_list = [ 
 [ "Antiperspirant / Deodorant", "Personal Care" ],
 [ "Toilet paper", "Personal Care" ],
 [ "Toothpaste", "Personal Care" ],
 [ "Paper towels", "Kitchen" ],
 [ "Sandwich / Freezer bags", "Kitchen" ],
 [ "Diapers", "Baby stuff" ],
 [ "Wipes", "Baby stuff" ],
 [ "Dishwasher detergent", "Cleaning products" ],
 [ "Garbage bags", "Cleaning products" ],
 [ "Laundry detergent", "Cleaning products" ],
 [ "Envelopes", "Office Supplies" ],
 [ "Postage stamps", "Office Supplies" ],
 [ "Batteries", "Other" ],
 [ "Light bulbs", "Other" ],
 ]

other_list.each do |name,  category|
  Other.create( name: name,  category: category)
end