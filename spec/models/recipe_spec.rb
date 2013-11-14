require 'spec_helper'

describe Recipe do

  it {should respond_to(:recipe)}
  it {should respond_to(:ingredient)}
  it {should respond_to(:recipe_ingredient)}

end
