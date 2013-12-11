class CreateListOthers < ActiveRecord::Migration
  def change
    create_table :list_others do |t|
      t.integer :list_id
      t.integer :other_id

      t.timestamps
    end
  end
end
