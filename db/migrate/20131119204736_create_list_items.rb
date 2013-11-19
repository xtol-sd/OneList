class CreateListItems < ActiveRecord::Migration
  def change
    create_table :list_items do |t|
      t.integer :list_id
      t.integer :item_id
      t.integer :item_amount
      t.string :comment
    end
  end
end
