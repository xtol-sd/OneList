require 'spec_helper'

describe Recipe do

  it {should respond_to(:ingredients)}
  it {should respond_to(:recipe_ingredients)}
end
