class AddColumnDefaultAmountToItems < ActiveRecord::Migration
  def change
    add_column :items, :default_amount, :float
  end
end
