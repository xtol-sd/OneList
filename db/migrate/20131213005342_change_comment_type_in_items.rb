class ChangeCommentTypeInItems < ActiveRecord::Migration
  def change
  	change_column :items, :comment, :text
  end
end
