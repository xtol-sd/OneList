class CreateOthers < ActiveRecord::Migration
  def change
    create_table :others do |t|
      t.string :name
      t.string :comment
      t.string :category      
      t.boolean :selected
      t.float :default_amount
      t.string :default_unit

      t.timestamps
    end
  end
end
