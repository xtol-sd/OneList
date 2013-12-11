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
 [ "Bath soap", "Personal Care" ],
 [ "Hand soap", "Personal Care" ],
 [ "Condoms / Other b.c.", "Personal Care" ],
 [ "Cosmetics", "Personal Care" ],
 [ "Cotton swabs", "Personal Care" ],
 [ "Facial cleanser", "Personal Care" ],
 [ "Facial tissue", "Personal Care" ],
 [ "Feminine products", "Personal Care" ],
 [ "Floss", "Personal Care" ],
 [ "Hair gel / Spray", "Personal Care" ],
 [ "Lip balm", "Personal Care" ],
 [ "Moisturizing lotion", "Personal Care" ],
 [ "Mouthwash", "Personal Care" ],
 [ "Razors", "Personal Care" ],
 [ "Shaving cream", "Personal Care" ],
 [ "Shampoo", "Personal Care" ],
 [ "Conditioner", "Personal Care" ],
 [ "Sunblock", "Personal Care" ],
 [ "Toilet paper", "Personal Care" ],
 [ "Toothpaste", "Personal Care" ],
 [ "Vitamins / Supplements", "Personal Care" ],
 [ "Allergy", "Medicine" ],
 [ "Antibiotic", "Medicine" ],
 [ "Antidiarrheal", "Medicine" ],
 [ "Aspirin", "Medicine" ],
 [ "Antacid", "Medicine" ],
 [ "Band-aids / Medical", "Medicine" ],
 [ "Cold / Flu / Sinus", "Medicine" ],
 [ "Pain reliever", "Medicine" ],
 [ "Prescription pick-up", "Medicine" ],
 [ "Aluminum foil", "Kitchen" ],
 [ "Napkins", "Kitchen" ],
 [ "Non-stick spray", "Kitchen" ],
 [ "Paper towels", "Kitchen" ],
 [ "Plastic wrap", "Kitchen" ],
 [ "Sandwich / Freezer bags", "Kitchen" ],
 [ "Wax paper", "Kitchen" ],
 [ "Baby food", "Baby stuff" ],
 [ "Diapers", "Baby stuff" ],
 [ "Formula", "Baby stuff" ],
 [ "Lotion", "Baby stuff" ],
 [ "Baby wash", "Baby stuff" ],
 [ "Wipes", "Baby stuff" ],
 [ "Air freshener", "Cleaning products" ],
 [ "Bathroom cleaner", "Cleaning products" ],
 [ "Bleach", "Cleaning products" ],
 [ "Dish soap", "Cleaning products" ],
 [ "Dishwasher detergent", "Cleaning products" ],
 [ "Garbage bags", "Cleaning products" ],
 [ "Glass cleaner", "Cleaning products" ],
 [ "Laundry detergent", "Cleaning products" ],
 [ "Mop head", "Cleaning products" ],
 [ "Vacuum bags", "Cleaning products" ],
 [ "Sponges / Scrubbers", "Cleaning products" ],
 [ "CDRs / DVDRs", "Office Supplies" ],
 [ "Notepad", "Office Supplies" ],
 [ "Envelopes", "Office Supplies" ],
 [ "Glue", "Office Supplies" ],
 [ "Tape", "Office Supplies" ],
 [ "Paper", "Office Supplies" ],
 [ "Pens / Pencils", "Office Supplies" ],
 [ "Postage stamps", "Office Supplies" ],
 [ "Automotive", "Other" ],
 [ "Batteries", "Other" ],
 [ "Charcoal / Propane", "Other" ],
 [ "Flowers", "Other" ],
 [ "Greeting card", "Other" ],
 [ "Insect repellent", "Other" ],
 [ "Light bulbs", "Other" ],
 [ "Newspaper", "Other" ],
 [ "Magazine", "Other" ]
 ]

other_list.each do |name,  category|
  Other.create( name: name,  category: category)
end