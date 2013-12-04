class AddColumnDefaultUnitToItems < ActiveRecord::Migration
  def change
    add_column :items, :default_unit, :string
  end
end
