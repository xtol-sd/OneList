require 'spec_helper'

describe Ingredient do
  it {should respond_to(:recipes)}
  it {should respond_to(:recipe_ingredients)}
end
